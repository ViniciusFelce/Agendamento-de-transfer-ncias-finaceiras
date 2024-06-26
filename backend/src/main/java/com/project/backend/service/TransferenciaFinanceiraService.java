package com.project.backend.service;

import com.project.backend.model.ContaBancaria;
import com.project.backend.model.TransferenciaFinanceira;
import com.project.backend.repository.ContaBancariaRepository;
import com.project.backend.repository.TransferenciaFinanceiraRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TransferenciaFinanceiraService {

    private final TransferenciaFinanceiraRepository transferenciaFinanceiraRepository;
    private final ContaBancariaRepository contaBancariaRepository;

    @Autowired
    public TransferenciaFinanceiraService(TransferenciaFinanceiraRepository transferenciaFinanceiraRepository,
                                          ContaBancariaRepository contaBancariaRepository) {
        this.transferenciaFinanceiraRepository = transferenciaFinanceiraRepository;
        this.contaBancariaRepository = contaBancariaRepository;
    }

    @Transactional
    public TransferenciaFinanceira agendarTransferencia(Long contaOrigemId, Long contaDestinoId, BigDecimal valor, LocalDate dataAgendada) {
        // Verificar se contaOrigemId é menor que contaDestinoId
        if (contaOrigemId >= contaDestinoId) {
            throw new IllegalArgumentException("O valor de contaOrigemId deve ser menor que contaDestinoId.");
        }

        // Buscar contas bancárias
        Optional<ContaBancaria> contaOrigemOptional = contaBancariaRepository.findById(contaOrigemId);
        Optional<ContaBancaria> contaDestinoOptional = contaBancariaRepository.findById(contaDestinoId);

        if (contaOrigemOptional.isEmpty() || contaDestinoOptional.isEmpty()) {
            throw new IllegalArgumentException("Conta bancária de origem ou destino não encontrada.");
        }

        ContaBancaria contaOrigem = contaOrigemOptional.get();
        ContaBancaria contaDestino = contaDestinoOptional.get();

        // Verificar saldo da conta de origem
        BigDecimal saldoOrigem = contaOrigem.getSaldo();
        if (saldoOrigem.compareTo(valor) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente na conta de origem.");
        }

        // Criar a transferência financeira agendada
        TransferenciaFinanceira transferencia = new TransferenciaFinanceira();
        transferencia.setContaOrigem(contaOrigem);
        transferencia.setContaDestino(contaDestino);
        transferencia.setValor(valor);
        transferencia.setDataAgendada(dataAgendada);
        transferencia.setStatus("AGENDADA");

        // Salvar a transferência financeira agendada
        transferencia = transferenciaFinanceiraRepository.save(transferencia);

        // Atualizar saldos das contas bancárias
        BigDecimal novoSaldoOrigem = saldoOrigem.subtract(valor);
        contaOrigem.setSaldo(novoSaldoOrigem);

        BigDecimal novoSaldoDestino = contaDestino.getSaldo().add(valor);
        contaDestino.setSaldo(novoSaldoDestino);

        contaBancariaRepository.save(contaOrigem);
        contaBancariaRepository.save(contaDestino);

        return transferencia;
    }

    public List<TransferenciaFinanceira> listarTransferencias() {
        return transferenciaFinanceiraRepository.findAll();
    }

    public TransferenciaFinanceira buscarTransferenciaPorId(Long id) {
        return transferenciaFinanceiraRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Transferência não encontrada."));
    }
}
