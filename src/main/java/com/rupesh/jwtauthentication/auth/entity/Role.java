package com.rupesh.jwtauthentication.auth.entity;

import com.rupesh.jwtauthentication.auth.constants.ERole;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private ERole eRole;

    @Override
    public String getAuthority() {
        return eRole.toString();
    }
}
