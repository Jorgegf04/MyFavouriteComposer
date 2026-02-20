package com.example.favourite_composer.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI myOpenAPI() {
        Server prodServer = new Server();
        prodServer.setUrl("http://localhost:9091");
        prodServer.setDescription("Server URL in Production environment");
        Contact contact = new Contact();
        contact.setEmail("jgfestudios@gmail.com");
        contact.setName("Jorge Guijarro Fuentes");
        contact.setUrl("https://www.mycompany.com");
        License mitLicense = new License()
                .name("MIT License")
                .url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("API Documentation Example")
                .version("1.0")
                .contact(contact)
                .description("This API is an example of Swagger usage")
                .termsOfService("https://www.mycompany.com/terms")
                .license(mitLicense);
        return new OpenAPI().info(info).servers(List.of(prodServer));
    }
}