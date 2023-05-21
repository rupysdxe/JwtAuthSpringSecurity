package com.rupesh.jwtauthentication.mapper;

import com.rupesh.jwtauthentication.auth.dto.UserDto;
import com.rupesh.jwtauthentication.auth.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public  interface UserMapper extends EntityMapper<User, UserDto>{
}
