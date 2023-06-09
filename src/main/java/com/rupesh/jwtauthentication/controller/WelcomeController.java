package com.rupesh.jwtauthentication.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/welcome")
public class WelcomeController {

    @GetMapping
    public ResponseEntity<?> welcome(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok("Welcome "+username);
    }
}
