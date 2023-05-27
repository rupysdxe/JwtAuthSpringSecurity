package com.rupesh.jwtauthentication.auth.serviceimpl;

import com.rupesh.jwtauthentication.auth.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class JwtServiceImpl implements JwtService
{
    @Value("${values.app.jwtSecret}")
    private String jwtSecret;
    @Value("${values.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Override
    public String generateToken(UserDetails userDetails){
        Date curr = new Date(System.currentTimeMillis());
        List<String> roles = userDetails
                .getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

       return Jwts.builder()
               .setSubject(userDetails.getUsername())
               .claim("roles", roles)
               .setIssuedAt(curr)
               .setExpiration(new Date(curr.getTime()+jwtExpirationMs))
               .signWith(SignatureAlgorithm.HS512, jwtSecret)
               .compact();
    }

    @Override
    public String getUsernameFromToken(String token){
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    @Override
    public boolean isTokenExpired(String token){
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration().before(new Date(System.currentTimeMillis()));
    }


    @Override
    public boolean isTokenValid(String token){
        return !isTokenExpired(token);
    }

    @Override
    public Collection<GrantedAuthority> extractAuthoritiesFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token).getBody();
        List<?> roles = (List<?>) claims.get("roles");
        return roles.stream()
                .map(role -> (GrantedAuthority) () -> (String) role).collect(Collectors.toList());

    }


}
