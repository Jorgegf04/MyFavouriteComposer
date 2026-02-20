package com.example.favourite_composer.user.service;

import com.example.favourite_composer.user.domain.Rol;
import com.example.favourite_composer.user.domain.User;
import com.example.favourite_composer.user.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Used by Spring Security during authentication
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String name)
            throws UsernameNotFoundException {

        User user = userRepository.findByName(name);

        if (user == null) {
            throw new UsernameNotFoundException(
                    "User not found: " + name);
        }

        return UserDetailsImpl.build(user);
    }

    public User addUser(User user) {

        if (userRepository.existsByName(user.getName())) {
            return null;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getRol() == null) {
            user.setRol(Rol.USER);
        }

        return userRepository.save(user);
    }

}
