package com.example.favourite_composer.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.favourite_composer.user.domain.Rol;
import com.example.favourite_composer.user.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);

    Boolean existsByName(String name);

    Boolean existsByRol(Rol rol);
}
