package com.project.backend.controller;

import com.project.backend.dto.AgendamentoTransferenciaDTO;
import com.project.backend.dto.TransferenciaFinanceiraDTO;
import com.project.backend.model.TransferenciaFinanceira;
import com.project.backend.service.TransferenciaFinanceiraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/transferencias")
public class TransferenciaFinanceiraController {
    private final TransferenciaFinanceiraService transferenciaFinanceiraService;

    @Autowired
    public TransferenciaFinanceiraController(TransferenciaFinanceiraService transferenciaFinanceiraService) {
        this.transferenciaFinanceiraService = transferenciaFinanceiraService;
    }

    @PostMapping("/agendar")
    public ResponseEntity<TransferenciaFinanceiraDTO> agendarTransferencia(@RequestBody AgendamentoTransferenciaDTO agendamentoTransferenciaDTO) {
        TransferenciaFinanceira transferencia = transferenciaFinanceiraService.agendarTransferencia(
                agendamentoTransferenciaDTO.getContaOrigemId(),
                agendamentoTransferenciaDTO.getContaDestinoId(),
                agendamentoTransferenciaDTO.getValor(),
                agendamentoTransferenciaDTO.getDataAgendada()
        );
        TransferenciaFinanceiraDTO novaTransferenciaDTO = new TransferenciaFinanceiraDTO(
                transferencia.getId(),
                transferencia.getContaOrigem().getId(),
                transferencia.getContaDestino().getId(),
                transferencia.getValor(),
                transferencia.getDataAgendada(),
                transferencia.getStatus()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(novaTransferenciaDTO);
    }

    @GetMapping
    public ResponseEntity<List<TransferenciaFinanceiraDTO>> listarTransferencias() {
        List<TransferenciaFinanceira> transferencias = transferenciaFinanceiraService.listarTransferencias();
        List<TransferenciaFinanceiraDTO> transferenciasDTO = transferencias.stream()
                .map(transferencia -> new TransferenciaFinanceiraDTO(
                        transferencia.getId(),
                        transferencia.getContaOrigem().getId(),
                        transferencia.getContaDestino().getId(),
                        transferencia.getValor(),
                        transferencia.getDataAgendada(),
                        transferencia.getStatus()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(transferenciasDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransferenciaFinanceiraDTO> buscarTransferenciaPorId(@PathVariable Long id) {
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
