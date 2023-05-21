package com.rupesh.jwtauthentication.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/welcome")
public class WelcomeController {

    @GetMapping
    public String welcome(){
        return "Welcome to the world of Spring Security";
    }
}
