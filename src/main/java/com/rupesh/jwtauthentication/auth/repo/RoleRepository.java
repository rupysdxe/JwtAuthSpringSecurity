package com.rupesh.jwtauthentication.auth.repo;

import com.rupesh.jwtauthentication.auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
}
