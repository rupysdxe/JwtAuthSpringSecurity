package com.rupesh.jwtauthentication.auth.serviceimpl;

import com.rupesh.jwtauthentication.auth.service.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

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
       return Jwts.builder()
               .setSubject(userDetails.getUsername())
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
    public boolean isTokenValid(String token, UserDetails userDetails){
        return !isTokenExpired(token) && getUsernameFromToken(token).equals(userDetails.getUsername());
    }


}
