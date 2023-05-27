package com.rupesh.jwtauthentication.auth.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
     String generateToken(UserDetails userDetails);
     String getUsernameFromToken(String token);
     boolean isTokenExpired(String token);
     boolean isTokenValid(String token, UserDetails userDetails);

}
