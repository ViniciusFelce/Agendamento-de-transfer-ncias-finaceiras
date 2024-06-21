package com.project.backend.service;

import com.project.backend.dto.ClienteDTO;
import com.project.backend.model.Cliente;
import com.project.backend.model.ContaBancaria;
import com.project.backend.repository.ClienteRepository;
import com.project.backend.repository.ContaBancariaRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final ContaBancariaRepository contaBancariaRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository, ContaBancariaRepository contaBancariaRepository) {
        this.clienteRepository = clienteRepository;
        this.contaBancariaRepository = contaBancariaRepository;
    }

    public List<Cliente> listarClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        clientes.forEach(cliente -> Hibernate.initialize(cliente.getContas()));
        return clientes;
    }

    public Cliente buscarClientePorId(Long id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (clienteOptional.isEmpty()) {
            throw new IllegalArgumentException("Cliente não encontrado.");
        }
        return clienteOptional.get();
    }

    public Cliente criarCliente(ClienteDTO clienteDTO) {
        List<ContaBancaria> contas = clienteDTO.getContaIds().stream()
                .map(contaId -> contaBancariaRepository.findById(contaId)
                        .orElseThrow(() -> new IllegalArgumentException("Conta bancária não encontrada com ID: " + contaId)))
                .collect(Collectors.toList());

        Cliente cliente = new Cliente();
        cliente.setNome(clienteDTO.getNome());
        cliente.setCpf(clienteDTO.getCpf());
        cliente.setEndereco(clienteDTO.getEndereco());
        cliente.setContas(contas);

        return clienteRepository.save(cliente);
    }

    public void deletarCliente(Long id) {
        clienteRepository.deleteById(id);
    }
}
