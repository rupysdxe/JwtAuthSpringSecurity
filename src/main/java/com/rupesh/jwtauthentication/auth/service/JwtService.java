package com.rupesh.jwtauthentication.auth.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public interface JwtService {
     String generateToken(UserDetails userDetails);
     String getUsernameFromToken(String token);
     boolean isTokenExpired(String token);
     boolean isTokenValid(String token);
     Collection<GrantedAuthority> extractAuthoritiesFromToken(String token);

}
