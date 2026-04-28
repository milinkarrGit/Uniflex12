package com.uniflex.app;

import com.uniflex.app.entity.User;
import com.uniflex.app.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class UniflexApplication {

    public static void main(String[] args) {
        SpringApplication.run(UniflexApplication.class, args);
    }

    @Bean
    CommandLineRunner initUsers(UserRepository userRepo, PasswordEncoder encoder) {
        return args -> {

            if (userRepo.findByUsername("admin").isEmpty()) {
                User u = new User();
                u.setUsername("admin");
                u.setPassword(encoder.encode("admin123"));
                u.setRole("ROLE_ADMIN");
                userRepo.save(u);
                System.out.println("✅ Admin créé : admin / admin123");
            }

            if (userRepo.findByUsername("prof").isEmpty()) {
                User u = new User();
                u.setUsername("prof");
                u.setPassword(encoder.encode("prof123"));
                u.setRole("ROLE_TEACHER");
                userRepo.save(u);
                System.out.println("✅ Prof créé : prof / prof123");
            }
            if (userRepo.findByUsername("jean").isEmpty()) {
                User u = new User();
                u.setUsername("jean");
                u.setPassword(encoder.encode("jean12"));
                u.setRole("ROLE_STUDENT");
                userRepo.save(u);
                System.out.println("✅ jean créé : prof / jean12");
            }
        };
    }
}
