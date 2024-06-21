package com.project.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AgendamentoTransferenciaDTO {
    private Long id;
    private Long contaOrigemId;
    private Long contaDestinoId;
    private BigDecimal valor;
    private LocalDate dataAgendada;

    public AgendamentoTransferenciaDTO() {
    }

    public AgendamentoTransferenciaDTO(Long id, Long contaOrigemId, Long contaDestinoId, BigDecimal valor, LocalDate dataAgendada) {
        this.id = id;
        this.contaOrigemId = contaOrigemId;
        this.contaDestinoId = contaDestinoId;
        this.valor = valor;
        this.dataAgendada = dataAgendada;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getContaOrigemId() {
        return contaOrigemId;
    }
    public void setContaOrigemId(Long contaOrigemId) {
        this.contaOrigemId = contaOrigemId;
    }

    public Long getContaDestinoId() {
        return contaDestinoId;
    }
    public void setContaDestinoId(Long contaDestinoId) {
        this.contaDestinoId = contaDestinoId;
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
}
