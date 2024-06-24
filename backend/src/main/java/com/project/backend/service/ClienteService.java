package com.project.backend.service;

import com.project.backend.dto.ClienteDTO;
import com.project.backend.dto.ContaBancariaDTO;
import com.project.backend.model.Cliente;
import com.project.backend.model.ContaBancaria;
import com.project.backend.repository.ClienteRepository;
import com.project.backend.repository.ContaBancariaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService implements UserDetailsService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ContaBancariaRepository contaBancariaRepository;

    @Transactional
    public void criarClienteComConta(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setNome(clienteDTO.getNome());
        cliente.setCpf(clienteDTO.getCpf());
        cliente.setUsername(clienteDTO.getEmail());
        cliente.setPassword(clienteDTO.getSenha());
        cliente.setEndereco(clienteDTO.getEndereco());

        if (clienteDTO.getContas() != null) {
            for (ContaBancariaDTO contaDTO : clienteDTO.getContas()) {
                ContaBancaria conta = new ContaBancaria();
                conta.setAgencia(contaDTO.getAgencia());
                conta.setBanco(contaDTO.getBanco());
                conta.setNumero(contaDTO.getNumero());
                conta.setSaldo(contaDTO.getSaldo());
                conta.setCliente(cliente); // Associar a conta ao cliente
                cliente.getContas().add(conta); // Adicionar a conta à lista de contas do cliente
            }
        }

        clienteRepository.save(cliente);
    }

    @Transactional(readOnly = true)
    public List<Cliente> listarClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        clientes.forEach(cliente -> cliente.getContas().size()); // Forçar a inicialização das contas
        return clientes;
    }

    @Transactional(readOnly = true)
    public Cliente buscarClientePorId(Long id) {
        Cliente cliente = clienteRepository.findById(id).orElse(null);
        if (cliente != null) {
            cliente.getContas().size(); // Forçar a inicialização das contas
        }
        return cliente;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Cliente cliente = clienteRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Email Não encontrado: " + username));

        return ServiceUsuariosDetailsImp.build(cliente);
    }

}
