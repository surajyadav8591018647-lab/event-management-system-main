package com.example.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.RegisterResponse;
import com.example.demo.dto.RegisterUserRequest;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtService;
import com.example.demo.exception.RegistrationException;
import com.example.demo.exception.LoginException;

@Service
public class UserService {

	private UserRepository userRepo;
	private PasswordEncoder passEncode;
	private JwtService  jwtService;

	public UserService(UserRepository userRepo, PasswordEncoder passEncode, JwtService jwtService) {
		this.userRepo = userRepo;
		this.passEncode = passEncode;
		this.jwtService = jwtService;
	}
	
	public RegisterResponse registerUser(RegisterUserRequest ur) {

		if (ur.getName() == null || ur.getName().isBlank()) {
        throw new RegistrationException("Username is required");
    }

    if (!ur.getName().matches("^[a-zA-Z0-9_]{3,20}$")) {
        throw new RegistrationException(
                "Username must be 3-20 characters and contain only letters, numbers or underscore"
        );
    }

    if (ur.getEmail() == null || ur.getEmail().isBlank()) {
        throw new RegistrationException("Email is required");
    }

    if (!ur.getEmail().matches(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
        throw new RegistrationException("Enter a valid email address");
    }

    if (ur.getPassword() == null || ur.getPassword().length() < 6) {
        throw new RegistrationException(
                "Password must contain at least 6 characters"
        );
    }
		
		if(userRepo.findByName(ur.getName()).isPresent()) {
			throw new RegistrationException("Username already registered");
		}
		
		if(userRepo.findByEmail(ur.getEmail()).isPresent()) {
			throw new RegistrationException("Email already registered");
		}
		User u = new User();
		u.setName(ur.getName());
		u.setPassword(passEncode.encode(ur.getPassword()));
		u.setEmail(ur.getEmail());
		u.setRole("USER");
		User savedUser =  userRepo.save(u);

		return new RegisterResponse("User registered successfully", savedUser.getId(), 
		savedUser.getName(), savedUser.getEmail(),savedUser.getRole());
	}
	
	public LoginResponse loginUser(LoginRequest log) {

		if (log.getIdentifier() == null ||
            log.getIdentifier().isBlank()) {

        throw new LoginException(
                "Username or email is required"
        );
    }

    if (log.getPassword() == null ||
            log.getPassword().isBlank()) {

        throw new LoginException(
                "Password is required"
        );
    }

		String identifier = log.getIdentifier();
		
		User us = userRepo.findByEmail(identifier).orElseGet(() -> userRepo
		.findByName(identifier)
		.orElseThrow(() -> new LoginException("Invalid Username/Password")));
		
		if(! passEncode.matches(log.getPassword(), us.getPassword())) {
			throw new LoginException("Invalid email or password");
			
		}

		
		String token = jwtService.generateToken(us.getEmail(), us.getRole());
		return new LoginResponse(token, us.getRole());
	}
	
	
	
}
