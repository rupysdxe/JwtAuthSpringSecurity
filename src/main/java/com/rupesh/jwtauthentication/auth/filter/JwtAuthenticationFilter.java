package com.rupesh.jwtauthentication.auth.filter;

import com.rupesh.jwtauthentication.auth.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component // We need to register this filter as a bean
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {
        log.info("Validating Token");
        final String authorizationHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;
        final Collection<GrantedAuthority> authorities;
        // First we check if the request has Authorization Header
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            // If it has Authorization Header then we extract the jwt token
            jwt = authorizationHeader.substring(7);
            username = jwtService.getUsernameFromToken(jwt);
            authorities = jwtService.extractAuthoritiesFromToken(jwt);

            // We check if the username is not null and the user is not already authenticated
            if(username!=null && !username.isEmpty() && SecurityContextHolder.getContext().getAuthentication()==null)
            {
                // We check if the token is valid
                if(jwtService.isTokenValid(jwt)){
                    // If the token is valid then we set the authentication in the context
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =  new
                            UsernamePasswordAuthenticationToken(username, null, authorities);
                    // We set the details of the authentication
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // We set the authentication in the context
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }//
            }
        }else
        {
            // If the Authorization Header is not found
            log.info("Authorization Header not found.");
        }
        // We pass the request and response to the next filter
        filterChain.doFilter(request,response);

    }// method
}
