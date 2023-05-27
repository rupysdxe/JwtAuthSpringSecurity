package com.rupesh.jwtauthentication.auth.config;


import com.rupesh.jwtauthentication.auth.filter.JwtAuthenticationFilter;
import com.rupesh.jwtauthentication.auth.filter.LoggingFilter;
import com.rupesh.jwtauthentication.auth.filter.TimingFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Slf4j
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final AuthenticationManager authenticationManager;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final LoggingFilter loggingFilter;

    private final TimingFilter  timingFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        //  CORS configuration
        http.cors().configurationSource(request -> corsConfiguration());

        http
                .csrf().disable()
                .authorizeRequests().mvcMatchers("/oauth/token/**").permitAll()
                .and()
                .authorizeRequests().anyRequest().authenticated();

        // Authentication configuration
        http.authenticationManager(authenticationManager);
        // add timing filter
        http.addFilterBefore(timingFilter, UsernamePasswordAuthenticationFilter.class);
        // add logging filter
        http.addFilterBefore(loggingFilter, UsernamePasswordAuthenticationFilter.class);
        // add jwt token filter
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        // We don't need sessions to be created.
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();

    }

    public CorsConfiguration corsConfiguration(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        return corsConfiguration;
    }




}