package com.rupesh.jwtauthentication.auth.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import java.util.Set;

@Entity
@Getter
@Setter
public class User {
    @Id
    @Email
    private String email;
    private String password;
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;
}
