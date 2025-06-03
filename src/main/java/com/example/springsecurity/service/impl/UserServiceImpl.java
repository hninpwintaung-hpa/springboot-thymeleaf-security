package com.example.springsecurity.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.springsecurity.mapper.Mapper;
import com.example.springsecurity.model.Role;
import com.example.springsecurity.model.User;
import com.example.springsecurity.model.dto.UserDto;
import com.example.springsecurity.repository.RoleRepository;
import com.example.springsecurity.repository.UserRepository;
import com.example.springsecurity.service.UserService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

	private final UserRepository userRepo;
	private final RoleRepository roleRepo;
	private final PasswordEncoder passwordEncoder;
	private final Mapper mapper;

	@Override
	@Transactional
	public UserDto saveUser(UserDto userDto) {
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		User user = mapper.map(userDto, User.class);

		Role role = roleRepo.findByName("ROLE_USER").orElseGet(() -> {
			Role newRole = new Role("ROLE_USER");
			return roleRepo.save(newRole);
		});

		user.getRoles().add(role);
		role.getUsers().add(user);

		userRepo.save(user);
		return userDto;
	}

	@Override
	public void addRoleToUser(String username, String roleName) {
		User user = userRepo.findByUsername(username).orElseThrow();
		Role role = roleRepo.findByName(roleName).orElseThrow();
		user.getRoles().add(role);
	}

	@Override
	public Optional<User> getUser(String username) {
		return userRepo.findByUsername(username);
	}

	@Override
	public List<User> getUsers() {
		return userRepo.findAll();
	}

	@Override
	public Role saveRole(Role role) {
		return roleRepo.save(role);
	}

	@Override
	public Optional<Role> getRoleByName(String role) {
		return roleRepo.findByName(role);
	}

	@Override
	public UserDto getUserById(Long id) {
		User user = userRepo.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
		return mapper.map(user, UserDto.class);
	}

	@Override
	public UserDto updateUser(UserDto userDto) {
		
	    User existingUser = userRepo.findById(userDto.getId())
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    existingUser.setUsername(userDto.getUsername());

	    if (userDto.getPassword() != null && !userDto.getPassword().isBlank()) {
	        existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
	    }

	    // 3. Handle roles (optional, depending on update scope)
	    Role role = roleRepo.findByName("ROLE_USER").orElseGet(() -> {
	        Role newRole = new Role("ROLE_USER");
	        return roleRepo.save(newRole);
	    });

	    existingUser.getRoles().clear(); // Optional: depends on whether you're replacing roles or adding
	    existingUser.getRoles().add(role);
	    role.getUsers().add(existingUser); // Optional: if maintaining bidirectional

	    User savedUser = userRepo.save(existingUser);

	    // 5. Return DTO (optional: map from saved entity if needed)
	    return mapper.map(savedUser, UserDto.class);
	}


}
