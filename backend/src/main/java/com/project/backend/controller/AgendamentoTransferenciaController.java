package com.project.backend.controller;


import com.project.backend.dto.TransferenciaFinanceiraDTO;
import com.project.backend.model.TransferenciaFinanceira;
import com.project.backend.service.TransferenciaFinanceiraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoTransferenciaController {

    private final TransferenciaFinanceiraService transferenciaFinanceiraService;

    @Autowired
    public AgendamentoTransferenciaController(TransferenciaFinanceiraService transferenciaFinanceiraService) {
        this.transferenciaFinanceiraService = transferenciaFinanceiraService;
    }

    @GetMapping
    public ResponseEntity<List<TransferenciaFinanceiraDTO>> listarAgendamentos() {
        List<TransferenciaFinanceira> agendamentos = transferenciaFinanceiraService.listarTransferencias();
        List<TransferenciaFinanceiraDTO> agendamentosDTO = agendamentos.stream()
                .map(transferencia -> new TransferenciaFinanceiraDTO(
                        transferencia.getId(),
                        transferencia.getContaOrigem().getId(),
                        transferencia.getContaDestino().getId(),
                        transferencia.getValor(),
                        transferencia.getDataAgendada(),
                        transferencia.getStatus()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(agendamentosDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransferenciaFinanceiraDTO> buscarAgendamentoPorId(@PathVariable Long id) {
        TransferenciaFinanceira transferencia = transferenciaFinanceiraService.buscarTransferenciaPorId(id);
        TransferenciaFinanceiraDTO transferenciaDTO = new TransferenciaFinanceiraDTO(
                transferencia.getId(),
                transferencia.getContaOrigem().getId(),
                transferencia.getContaDestino().getId(),
                transferencia.getValor(),
                transferencia.getDataAgendada(),
                transferencia.getStatus()
        );
        return ResponseEntity.ok(transferenciaDTO);
    }
}
