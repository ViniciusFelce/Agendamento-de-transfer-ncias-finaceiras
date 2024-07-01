package com.project.backend.service;

import com.project.backend.model.Cliente;
import com.project.backend.payload.LoginRequest;
import com.project.backend.payload.SignUpRequest;
import com.project.backend.repository.ClienteRepository;
import com.project.backend.repository.UserRepository;
import com.project.backend.security.JwtTokenUtil;
import jakarta.transaction.Transactional;
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
    private UserRepository signUpRequestRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public Cliente buscarClientePorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        cliente.getContas().size(); // Inicializa a coleção lazy
        return cliente;
    }

    public String authenticateUserAndGenerateJwt(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());

        return jwtTokenUtil.generateToken(userDetails);
    }

    public SignUpRequest registerUser(SignUpRequest signUpRequest) {

        signUpRequest.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        SignUpRequest savedUser = signUpRequestRepository.save(signUpRequest);

        return savedUser;
    }

    public SignUpRequest getUserDetailsByEmail(String email) {
        return signUpRequestRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public SignUpRequest buscarSignUpRequestPorClienteId(Long clienteId) {
        return signUpRequestRepository.findByClienteId(clienteId)
                .orElseThrow(() -> new RuntimeException("SignUpRequest não encontrado para o Cliente com ID: " + clienteId));
    }
}
