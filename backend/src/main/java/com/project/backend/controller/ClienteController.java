package com.project.backend.controller;

import com.project.backend.dto.ClienteDTO;
import com.project.backend.model.Cliente;
import com.project.backend.model.ContaBancaria;
import com.project.backend.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listarClientes() {
        List<ClienteDTO> clientes = clienteService.listarClientes().stream()
                .map(cliente -> new ClienteDTO(
                        cliente.getId(),
                        cliente.getNome(),
                        cliente.getCpf(),
                        cliente.getEndereco(),
                        cliente.getContas().stream().map(ContaBancaria::getId).collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarClientePorId(@PathVariable Long id) {
        Cliente cliente = clienteService.buscarClientePorId(id);
        ClienteDTO clienteDTO = new ClienteDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getEndereco(),
                cliente.getContas().stream().map(ContaBancaria::getId).collect(Collectors.toList())
        );
        return ResponseEntity.ok(clienteDTO);
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> criarCliente(@RequestBody ClienteDTO clienteDTO) {
        Cliente novoCliente = clienteService.criarCliente(clienteDTO);
        ClienteDTO novoClienteDTO = new ClienteDTO(
                novoCliente.getId(),
                novoCliente.getNome(),
                novoCliente.getCpf(),
                novoCliente.getEndereco(),
                novoCliente.getContas().stream().map(ContaBancaria::getId).collect(Collectors.toList())
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(novoClienteDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id) {
        clienteService.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }
}
