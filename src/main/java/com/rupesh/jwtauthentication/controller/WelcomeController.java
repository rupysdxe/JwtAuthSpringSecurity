package com.rupesh.jwtauthentication.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/welcome")
public class WelcomeController {

    @GetMapping
    public ResponseEntity<?> welcome(){
        return ResponseEntity.ok("Welcome to jwt authentication");
    }
}
