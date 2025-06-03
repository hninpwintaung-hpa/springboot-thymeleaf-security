package com.example.springsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.springsecurity.model.dto.UserDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AuthController {

	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}

	@GetMapping("/home")
	public String home() {
		return "home";
	}

	@GetMapping("/register")
	public String registerPage(Model model) {
		model.addAttribute("user", new UserDto());
		return "register";
	}
//
//	@GetMapping("/logout")
//	public String logout() {
//		return "login";
//	}
}
