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
import java.time.temporal.ChronoUnit;
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

        // Calcular a taxa conforme as condições
        BigDecimal taxa = calcularTaxa(dataAgendada, valor);
        if (taxa != null) {
            BigDecimal valorComTaxa = valor.add(valor.multiply(taxa));
            valor = valorComTaxa.setScale(2, BigDecimal.ROUND_HALF_UP);
        } else {
            throw new IllegalArgumentException("Transferência não permitida devido à taxa.");
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

    private BigDecimal calcularTaxa(LocalDate dataAgendada, BigDecimal valor) {
        long diasAteAgendamento = ChronoUnit.DAYS.between(LocalDate.now(), dataAgendada);

        if (valor.compareTo(BigDecimal.valueOf(3.00)) == 0) {
            // Se o valor for exatamente 3.00 e agendado imediato, taxa de 2.5%
            if (diasAteAgendamento == 0) {
                return BigDecimal.valueOf(0.025);
            } else {
                return null; // Transferência não permitida por taxa
            }
        } else if (valor.compareTo(BigDecimal.valueOf(12.00)) == 0 && diasAteAgendamento >= 1 && diasAteAgendamento <= 10) {
            // Valor exato de 12.00 com agendamento de 1 a 10 dias, taxa de 0.0%
            return BigDecimal.valueOf(0.0);
        } else if (diasAteAgendamento >= 11 && diasAteAgendamento <= 20) {
            // Agendamento de 11 a 20 dias, taxa de 8.2%
            return BigDecimal.valueOf(0.082);
        } else if (diasAteAgendamento >= 21 && diasAteAgendamento <= 30) {
            // Agendamento de 21 a 30 dias, taxa de 6.9%
            return BigDecimal.valueOf(0.069);
        } else if (diasAteAgendamento >= 31 && diasAteAgendamento <= 40) {
            // Agendamento de 31 a 40 dias, taxa de 4.7%
            return BigDecimal.valueOf(0.047);
        } else if (diasAteAgendamento >= 41 && diasAteAgendamento <= 50) {
            // Agendamento de 41 a 50 dias, taxa de 1.7%
            return BigDecimal.valueOf(0.017);
        } else {
            return null; // Transferência não permitida por taxa
        }
    }

    public List<TransferenciaFinanceira> listarTransferencias() {
        return transferenciaFinanceiraRepository.findAll();
    }

    public TransferenciaFinanceira buscarTransferenciaPorId(Long id) {
        return transferenciaFinanceiraRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Transferência não encontrada."));
    }
}
