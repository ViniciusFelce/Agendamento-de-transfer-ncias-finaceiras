package com.project.backend.controller;

import com.project.backend.dto.ClienteDTO;
import com.project.backend.dto.ContaBancariaDTO;
import com.project.backend.model.Cliente;
import com.project.backend.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<?> criarClienteComConta(@RequestBody ClienteDTO clienteDTO) {
        try {
            clienteService.criarClienteComConta(clienteDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar cliente com conta bancária: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listarClientes() {
        List<Cliente> clientes = clienteService.listarClientes();
        List<ClienteDTO> clienteDTOs = clientes.stream()
                .map(this::mapClienteToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(clienteDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarClientePorId(@PathVariable Long id) {
        Cliente cliente = clienteService.buscarClientePorId(id);
        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        ClienteDTO clienteDTO = mapClienteToDTO(cliente);
        return ResponseEntity.ok(clienteDTO);
    }

    private ClienteDTO mapClienteToDTO(Cliente cliente) {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(cliente.getId());
        clienteDTO.setNome(cliente.getNome());
        clienteDTO.setCpf(cliente.getCpf());
        clienteDTO.setEndereco(cliente.getEndereco());
        clienteDTO.setContas(cliente.getContas().stream()
                .map(conta -> new ContaBancariaDTO(
                        conta.getId(),
                        conta.getBanco(),
                        conta.getAgencia(),
                        conta.getNumero(),
                        conta.getSaldo()
                )).collect(Collectors.toList()));
        return clienteDTO;
    }
}
