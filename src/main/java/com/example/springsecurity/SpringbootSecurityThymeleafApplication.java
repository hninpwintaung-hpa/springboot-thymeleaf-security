package com.example.springsecurity;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.springsecurity.model.Role;
import com.example.springsecurity.model.User;
import com.example.springsecurity.repository.RoleRepository;
import com.example.springsecurity.repository.UserRepository;
import com.example.springsecurity.service.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class SpringbootSecurityThymeleafApplication {

	//private final UserService userService;
	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	private final RoleRepository roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootSecurityThymeleafApplication.class, args);
	}

//	@Bean
//	CommandLineRunner run() {
//		return args -> {
//			createDefaultAdminUser();
//		};
//	}
	
	  @Transactional
	    public void createDefaultAdminUser() {
	    	if (userRepo.findByUsername("admin").isEmpty()) {
	    		User admin = new User();
	    		admin.setUsername("admin");
	    		admin.setPassword(passwordEncoder.encode("admin123"));

	    		Role role = roleRepo.findByName("ROLE_ADMIN").orElseGet(() -> {
	    			Role newRole = new Role("ROLE_ADMIN");
	    			return roleRepo.save(newRole);
	    		});

	    		admin.getRoles().add(role);
	    		role.getUsers().add(admin);

	    		userRepo.save(admin);
	    	}
	    }
}
