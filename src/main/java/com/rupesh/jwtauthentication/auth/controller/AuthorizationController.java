package com.rupesh.jwtauthentication.auth.controller;


import com.rupesh.jwtauthentication.auth.entity.User;
import com.rupesh.jwtauthentication.auth.models.AuthenticationRequest;
import com.rupesh.jwtauthentication.auth.models.JwtToken;
import com.rupesh.jwtauthentication.auth.models.UserDetailsImpl;
import com.rupesh.jwtauthentication.auth.repo.UserRepository;
import com.rupesh.jwtauthentication.auth.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/oauth/token")
@RequiredArgsConstructor
@Slf4j
public class AuthorizationController {
    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;


    @PostMapping
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest){
        UserDetails userDetails = authenticateUser(authenticationRequest);
        JwtToken jwtToken = JwtToken.builder()
                .jwt(jwtService.generateToken(userDetails))
                .build();
        return ResponseEntity.ok(jwtToken);
    }

    private UserDetails authenticateUser(AuthenticationRequest authenticationRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        Optional<User> user = userRepository.findById(authenticationRequest.getEmail());
        if(!user.isPresent())throw new UsernameNotFoundException("User not found");
        return new UserDetailsImpl(user.get());
    }
}
