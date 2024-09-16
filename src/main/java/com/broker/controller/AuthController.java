package com.broker.controller;

import com.broker.provider.JwtTokenProvider;
import com.broker.entity.Customer;
import com.broker.entity.Role;
import com.broker.model.JwtAuthenticationResponse;
import com.broker.model.LoginRequest;
import com.broker.model.RegisterRequest;
import com.broker.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.Authentication;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken((UserDetails) authentication.getPrincipal());

        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        if (customerRepository.findByUserName(registerRequest.getUsername()).isPresent()) {
            return new ResponseEntity<>("Username is already taken", HttpStatus.BAD_REQUEST);
        }

        Customer customer = new Customer();
        customer.setUserName(registerRequest.getUsername());
        customer.setPassword(new BCryptPasswordEncoder().encode(registerRequest.getPassword()));
        customer.setName(registerRequest.getFullName());
        customer.setRole(Role.USER);

        customerRepository.save(customer);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
}
