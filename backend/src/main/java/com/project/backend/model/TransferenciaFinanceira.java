package com.project.backend.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transferencias_financeiras")
public class TransferenciaFinanceira {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "valor")
    private BigDecimal valor;

    @Column(name = "data_agendada")
    private LocalDate dataAgendada;

    @ManyToOne
    @JoinColumn(name = "conta_origem_id", referencedColumnName = "id")
    private ContaBancaria contaOrigem;

    @ManyToOne
    @JoinColumn(name = "conta_destino_id", referencedColumnName = "id")
    private ContaBancaria contaDestino;

    @Column(name = "status")
    private String status;

    public TransferenciaFinanceira() {
    }

    public TransferenciaFinanceira(Long id, BigDecimal valor, LocalDate dataAgendada, ContaBancaria contaOrigem, ContaBancaria contaDestino, String status) {
        this.id = id;
        this.valor = valor;
        this.dataAgendada = dataAgendada;
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getDataAgendada() {
        return dataAgendada;
    }

    public void setDataAgendada(LocalDate dataAgendada) {
        this.dataAgendada = dataAgendada;
    }

    public ContaBancaria getContaOrigem() {
        return contaOrigem;
    }

    public void setContaOrigem(ContaBancaria contaOrigem) {
        this.contaOrigem = contaOrigem;
    }

    public ContaBancaria getContaDestino() {
        return contaDestino;
    }

    public void setContaDestino(ContaBancaria contaDestino) {
        this.contaDestino = contaDestino;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
