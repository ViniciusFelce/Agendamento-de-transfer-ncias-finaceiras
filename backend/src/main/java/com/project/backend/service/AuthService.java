package com.project.backend.service;

import com.project.backend.payload.LoginRequest;
import com.project.backend.payload.SignUpRequest;
import com.project.backend.repository.UserRepository;
import com.project.backend.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    public String authenticateUserAndGenerateJwt(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());

        return jwtTokenUtil.generateToken(userDetails);
    }

    public SignUpRequest registerUser(SignUpRequest signUpRequest) {
        // Encrypt the password
        signUpRequest.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        // Save the user in the repository and return the saved entity
        SignUpRequest savedUser = userRepository.save(signUpRequest);

        return savedUser;
    }
}
