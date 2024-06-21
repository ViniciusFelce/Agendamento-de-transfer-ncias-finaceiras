package com.project.backend.service;

import com.project.backend.dto.ContaBancariaDTO;
import com.project.backend.model.ContaBancaria;
import com.project.backend.repository.ContaBancariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContaBancariaService {

    private final ContaBancariaRepository contaBancariaRepository;

    @Autowired
    public ContaBancariaService(ContaBancariaRepository contaBancariaRepository) {
        this.contaBancariaRepository = contaBancariaRepository;
    }

    public List<ContaBancariaDTO> listarContas() {
        return contaBancariaRepository.findAll().stream()
                .map(conta -> new ContaBancariaDTO(conta.getId(), conta.getBanco(), conta.getAgencia(), conta.getNumero(), conta.getSaldo()))
                .collect(Collectors.toList());
    }

    public ContaBancariaDTO buscarContaPorId(Long id) {
        Optional<ContaBancaria> contaOptional = contaBancariaRepository.findById(id);
        if (contaOptional.isEmpty()) {
            throw new IllegalArgumentException("Conta não encontrada.");
        }
        ContaBancaria conta = contaOptional.get();
        return new ContaBancariaDTO(conta.getId(), conta.getBanco(), conta.getAgencia(), conta.getNumero(), conta.getSaldo());
    }

    public ContaBancariaDTO salvarConta(ContaBancariaDTO contaDTO) {
        ContaBancaria conta = new ContaBancaria();
        conta.setBanco(contaDTO.getBanco());
        conta.setAgencia(contaDTO.getAgencia());
        conta.setNumero(contaDTO.getNumero());
        conta.setSaldo(contaDTO.getSaldo());
        ContaBancaria novaConta = contaBancariaRepository.save(conta);
        return new ContaBancariaDTO(novaConta.getId(), novaConta.getBanco(), novaConta.getAgencia(), novaConta.getNumero(), novaConta.getSaldo());
    }

    public void deletarConta(Long id) {
        contaBancariaRepository.deleteById(id);
    }
}
