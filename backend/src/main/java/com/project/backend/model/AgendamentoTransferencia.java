package com.project.backend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "agendamentos_transferencia")
public class AgendamentoTransferencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_agendamento")
    private LocalDateTime dataAgendamento;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "transferencia_id", referencedColumnName = "id")
    private TransferenciaFinanceira transferenciaFinanceira;

    public AgendamentoTransferencia() {
    }

    public AgendamentoTransferencia(Long id, LocalDateTime dataAgendamento, String status, TransferenciaFinanceira transferenciaFinanceira) {
        this.id = id;
        this.dataAgendamento = dataAgendamento;
        this.status = status;
        this.transferenciaFinanceira = transferenciaFinanceira;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(LocalDateTime dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TransferenciaFinanceira getTransferenciaFinanceira() {
        return transferenciaFinanceira;
    }

    public void setTransferenciaFinanceira(TransferenciaFinanceira transferenciaFinanceira) {
        this.transferenciaFinanceira = transferenciaFinanceira;
    }
}
