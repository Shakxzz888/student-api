package com.studentmanagementapi.controller;

import com.studentmanagementapi.model.User;
import com.studentmanagementapi.repository.UserRepository;
import com.studentmanagementapi.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtUtil jwtUtil;
	

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	
	@GetMapping("/test")
	public String test() {
		return "Working";
	}

	@PostMapping("/register") // optional endpoint to create users
	public User register(@RequestBody User user) {
	    user.setPassword(passwordEncoder.encode(user.getPassword()));
	    
	    if (user.getRole() == null) {
	    	user.setRole("USER"); // default role
	    }
	    
	    return userRepository.save(user);
	}

	@PostMapping("/login")
	public Map<String, String> login(@RequestBody Map<String, String> request) {

	    String username = request.get("username");
	    String password = request.get("password");

	    System.out.println("Login API Called");
	    
	    
	    User user = userRepository.findByUsername(username);

	//    if (user != null && user.getPassword().equals(password)) {
	    
	    if (user != null && passwordEncoder.matches(password, user.getPassword())) {
	    	
	    	

	        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());
	        
	        System.out.println("gettokenhere: " + token);

	        Map<String, String> response = new HashMap<>();
	        response.put("token", token);

	        return response;
	    }

	    throw new RuntimeException("Invalid credentials");
	}
}