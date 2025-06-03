package com.example.springsecurity.model.dto;

import java.util.Set;

import com.example.springsecurity.model.Role;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserDto {
	
	private Long id;
	
	@NotBlank(message = "{required.user.username}")
	private String username;

	@NotBlank(message = "{required.user.password}")
	private String password;
	
	private Set<Role> roles;
}
