package com.example.favourite_composer;

import com.example.favourite_composer.user.domain.User;
import com.example.favourite_composer.user.domain.Rol;
import com.example.favourite_composer.user.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class FavouriteComposerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FavouriteComposerApplication.class, args);
	}

	@Bean
	@Profile("!test")
	public CommandLineRunner initAdmin(
			UserRepository userRepository,
			PasswordEncoder passwordEncoder) {
		return args -> {

			User admin = new User();
			admin.setName("admin");
			admin.setPassword(passwordEncoder.encode("1234"));
			admin.setRol(Rol.ADMIN);

			userRepository.save(admin);

			User user1 = new User();
			user1.setName("jorge");
			user1.setPassword(passwordEncoder.encode("1234"));
			user1.setRol(Rol.USER);

			userRepository.save(user1);

		};
	}
}
