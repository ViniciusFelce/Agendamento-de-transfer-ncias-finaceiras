package com.project.backend.controller;

import com.project.backend.dto.ClienteDTO;
import com.project.backend.dto.ContaBancariaDTO;
import com.project.backend.dto.SignUpRequestDTO;
import com.project.backend.model.Cliente;
import com.project.backend.model.ContaBancaria;
import com.project.backend.payload.JwtResponse;
import com.project.backend.payload.LoginRequest;
import com.project.backend.payload.SignUpRequest;
import com.project.backend.payload.SignInResponse;
import com.project.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Validated @RequestBody LoginRequest loginRequest) {
        String jwt = authService.authenticateUserAndGenerateJwt(loginRequest);
        SignUpRequest userDetails = authService.getUserDetailsByEmail(loginRequest.getEmail());
        SignInResponse response = new SignInResponse(userDetails.getId(), userDetails.getEmail(), jwt);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequestDTO signUpRequestDTO) {
        SignUpRequest signUpRequest = convertToEntity(signUpRequestDTO);
        SignUpRequest savedUser = authService.registerUser(signUpRequest);
        SignUpRequestDTO responseDTO = convertToDTO(savedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/user-details/{id}")
    public ResponseEntity<?> getUserDetails(@PathVariable Long id) {
        Cliente cliente = authService.buscarClientePorId(id);
        SignUpRequest signUpRequest = authService.buscarSignUpRequestPorClienteId(id);
        SignUpRequestDTO signUpRequestDTO = convertClienteToSignUpRequestDTO(cliente, signUpRequest);
        return ResponseEntity.ok(signUpRequestDTO);
    }

    private SignUpRequest convertToEntity(SignUpRequestDTO signUpRequestDTO) {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setId(signUpRequestDTO.getId());
        signUpRequest.setEmail(signUpRequestDTO.getEmail());
        signUpRequest.setPassword(signUpRequestDTO.getPassword());
        Cliente cliente = new Cliente();
        cliente.setNome(signUpRequestDTO.getCliente().getNome());
        cliente.setCpf(signUpRequestDTO.getCliente().getCpf());
        cliente.setEndereco(signUpRequestDTO.getCliente().getEndereco());

        List<ContaBancaria> contas = signUpRequestDTO.getCliente().getContas().stream().map(contaBancariaDTO -> {
            ContaBancaria conta = new ContaBancaria();
            conta.setAgencia(contaBancariaDTO.getAgencia());
            conta.setBanco(contaBancariaDTO.getBanco());
            conta.setNumero(contaBancariaDTO.getNumero());
            conta.setSaldo(contaBancariaDTO.getSaldo());
            conta.setCliente(cliente);
            return conta;
        }).collect(Collectors.toList());

        cliente.setContas(contas);
        signUpRequest.setCliente(cliente);

        return signUpRequest;
    }

    private SignUpRequestDTO convertToDTO(SignUpRequest signUpRequest) {
        SignUpRequestDTO signUpRequestDTO = new SignUpRequestDTO();
        signUpRequestDTO.setId(signUpRequest.getId());
        signUpRequestDTO.setEmail(signUpRequest.getEmail());
        signUpRequestDTO.setPassword(signUpRequest.getPassword());

        Cliente cliente = signUpRequest.getCliente();
        signUpRequestDTO.setCliente(convertClienteToDTO(cliente));

        return signUpRequestDTO;
    }

    private ClienteDTO convertClienteToDTO(Cliente cliente) {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(cliente.getId());
        clienteDTO.setNome(cliente.getNome());
        clienteDTO.setCpf(cliente.getCpf());
        clienteDTO.setEndereco(cliente.getEndereco());

        List<ContaBancariaDTO> contasDTO = cliente.getContas().stream()
                .map(conta -> new ContaBancariaDTO(
                        conta.getId(),
                        conta.getBanco(),
                        conta.getAgencia(),
                        conta.getNumero(),
                        conta.getSaldo()
                )).collect(Collectors.toList());

        clienteDTO.setContas(contasDTO);

        return clienteDTO;
    }

    private SignUpRequestDTO convertClienteToSignUpRequestDTO(Cliente cliente, SignUpRequest signUpRequest) {
        SignUpRequestDTO signUpRequestDTO = new SignUpRequestDTO();
        signUpRequestDTO.setId(signUpRequest.getId());
        signUpRequestDTO.setEmail(signUpRequest.getEmail());
        signUpRequestDTO.setPassword(signUpRequest.getPassword());

        return signUpRequestDTO;
    }
}
