package com.rupesh.jwtauthentication.auth.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
public class JwtToken implements Serializable {
    private static final long serialVersionUID = 1L;

    private String jwt;


}
