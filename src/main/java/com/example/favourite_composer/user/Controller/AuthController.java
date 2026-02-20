package com.example.favourite_composer.user.Controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.favourite_composer.config.JwtUtils;
import com.example.favourite_composer.user.domain.Rol;
import com.example.favourite_composer.user.domain.User;
import com.example.favourite_composer.user.dto.JwtResponseDto;
import com.example.favourite_composer.user.dto.LoginDto;
import com.example.favourite_composer.user.dto.MessageResponse;
import com.example.favourite_composer.user.dto.SignupDto;
import com.example.favourite_composer.user.repository.UserRepository;
import com.example.favourite_composer.user.service.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

        @Autowired
        AuthenticationManager authenticationManager;

        @Autowired
        UserRepository userRepository;

        @Autowired
        PasswordEncoder encoder;

        @Autowired
        JwtUtils jwtUtils;

        @PostMapping("/signin")
        public ResponseEntity<?> authenticateUser(
                        @Valid @RequestBody LoginDto loginDto) {

                Authentication authentication = authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                loginDto.getNombre(),
                                                loginDto.getPassword()));

                SecurityContextHolder.getContext()
                                .setAuthentication(authentication);

                String jwt = jwtUtils.generateJwtToken(authentication);

                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

                String rol = userDetails.getAuthorities()
                                .stream()
                                .findFirst()
                                .map(a -> a.getAuthority())
                                .orElse("ERROR");

                return ResponseEntity.ok(
                                new JwtResponseDto(
                                                jwt,
                                                "Bearer",
                                                userDetails.getId(),
                                                userDetails.getUsername(),
                                                rol));
        }

        @PostMapping("/signup")
        public ResponseEntity<?> registerUser(
                        @Valid @RequestBody SignupDto signUpRequest) {

                if (userRepository.existsByName(signUpRequest.getName())) {
                        return ResponseEntity
                                        .badRequest()
                                        .body(new MessageResponse(
                                                        "Error: User already exists"));
                }

                User user = new User(
                                null,
                                signUpRequest.getName(),
                                encoder.encode(signUpRequest.getPassword()),
                                Rol.valueOf(signUpRequest.getRol()));

                userRepository.save(user);

                return ResponseEntity.ok(
                                new MessageResponse("User registered successfully"));
        }
}
