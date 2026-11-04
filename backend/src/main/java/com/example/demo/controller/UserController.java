package com.example.demo.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.RegisterResponse;
import com.example.demo.dto.RegisterUserRequest;

import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

	private final UserService  uService;

	public UserController(UserService uService) {
		this.uService = uService;
	}
	
	@PostMapping("/register")
	public RegisterResponse registerUser(@RequestBody RegisterUserRequest ur) {
		
		return uService.registerUser(ur);
	}
	
	@PostMapping("/login")
	public LoginResponse login(@RequestBody LoginRequest log) {
		return uService.loginUser(log);
	}
}
