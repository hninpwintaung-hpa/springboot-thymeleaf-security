package com.example.springsecurity.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.springsecurity.model.Role;
import com.example.springsecurity.model.dto.UserDto;
import com.example.springsecurity.service.RoleService;
import com.example.springsecurity.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController {

	private final UserService userService;
	private final RoleService roleService;

	@GetMapping
	public String listUsers(Model model) {
		model.addAttribute("users", userService.getUsers());
		return "users";
	}

	@PostMapping("/add")
	public String save(@ModelAttribute("user") @Valid UserDto userDto, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "register";
		}

		userService.saveUser(userDto);
		model.addAttribute("message", "New user has been registered successfully.");
		return "redirect:/";

	}

	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable Long id, Model model) {
		UserDto user = userService.getUserById(id);
		List<Role> allRoles = roleService.getAllRoles(); // Your service method

		model.addAttribute("user", user);
		model.addAttribute("allRoles", allRoles);
		
		return "edit-user";
	}

	@PostMapping("/users/update")
	public String updateUser(@ModelAttribute("user") UserDto user) {
		userService.updateUser(user); // Your service method to save changes
		return "redirect:/users"; // Back to user list
	}
}
