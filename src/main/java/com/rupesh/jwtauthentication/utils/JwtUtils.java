package com.rupesh.jwtauthentication.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

@Slf4j
public class JwtUtils {

    @Value("${values.app.jwtSecret}")
    private String jwtSecret;
    @Value("${values.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateToken(UserDetails userDetails){
        Date curr = new Date(System.currentTimeMillis());
       return Jwts.builder()
               .setSubject(userDetails.getUsername())
               .setIssuedAt(curr)
               .setExpiration(new Date(curr.getTime()+jwtExpirationMs))
               .signWith(SignatureAlgorithm.HS512, jwtSecret)
               .compact();

    }

    // retrieve username from jwt token
    public String getUsernameFromToken(String token){
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // check if the token has expired
    public boolean isTokenExpired(String token){
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration().before(new Date(System.currentTimeMillis()));

    }


}
