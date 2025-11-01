package com.hackathon.petstore_cms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hackathon.petstore_cms.entity.User;
import com.hackathon.petstore_cms.repository.UserRepository;

@SpringBootApplication
public class PetstoreCmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetstoreCmsApplication.class, args);
    }

}