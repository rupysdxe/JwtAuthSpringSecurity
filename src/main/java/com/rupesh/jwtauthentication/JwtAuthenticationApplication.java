package com.rupesh.jwtauthentication;

import com.rupesh.jwtauthentication.auth.constants.ERole;
import com.rupesh.jwtauthentication.auth.entity.Role;
import com.rupesh.jwtauthentication.auth.entity.User;
import com.rupesh.jwtauthentication.auth.repo.RoleRepository;
import com.rupesh.jwtauthentication.auth.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;


@SpringBootApplication
@RequiredArgsConstructor
public class JwtAuthenticationApplication {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(JwtAuthenticationApplication.class, args);
    }

    @PostConstruct
    public void init() {
        Role userRole = new Role();
        userRole.setERole(ERole.ROLE_USER);
        roleRepository.save(userRole);
        Role userAdmin = new Role();
        userAdmin.setERole(ERole.ROLE_ADMIN);
        roleRepository.save(userAdmin);
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        roles.add(userAdmin);
        User user = new User();
        user.setEmail("rupesh@gmail.com");
        user.setPassword(passwordEncoder.encode("1234"));
        user.setRoles(roles);
        userRepository.save(user);

    }

}
