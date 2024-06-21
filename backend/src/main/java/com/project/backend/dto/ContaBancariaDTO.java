package com.project.backend.dto;

import java.math.BigDecimal;

public class ContaBancariaDTO {
    private Long id;
    private String banco;
    private String agencia;
    private String numero;
    private BigDecimal saldo;

    public ContaBancariaDTO() {
    }

    public ContaBancariaDTO(Long id, String banco, String agencia, String numero, BigDecimal saldo) {
        this.id = id;
        this.banco = banco;
        this.agencia = agencia;
        this.numero = numero;
        this.saldo = saldo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
}
