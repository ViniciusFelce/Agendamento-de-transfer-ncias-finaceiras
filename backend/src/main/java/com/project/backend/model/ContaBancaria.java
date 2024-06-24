package com.project.backend.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "contas_bancarias")
public class ContaBancaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "banco")
    private String banco;

    @Column(name = "agencia")
    private String agencia;

    @Column(name = "numero")
    private String numero;

    @Column(name = "saldo")
    private BigDecimal saldo;

    @OneToMany(mappedBy = "contaOrigem", cascade = CascadeType.ALL)
    private List<TransferenciaFinanceira> transferenciasFeitas;

    @OneToMany(mappedBy = "contaDestino", cascade = CascadeType.ALL)
    private List<TransferenciaFinanceira> transferenciasRecebidas;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    public ContaBancaria() {
    }

    public ContaBancaria(Long id, String banco, String agencia, String numero, BigDecimal saldo) {
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

    public List<TransferenciaFinanceira> getTransferenciasFeitas() {
        return transferenciasFeitas;
    }

    public void setTransferenciasFeitas(List<TransferenciaFinanceira> transferenciasFeitas) {
        this.transferenciasFeitas = transferenciasFeitas;
    }

    public List<TransferenciaFinanceira> getTransferenciasRecebidas() {
        return transferenciasRecebidas;
    }

    public void setTransferenciasRecebidas(List<TransferenciaFinanceira> transferenciasRecebidas) {
        this.transferenciasRecebidas = transferenciasRecebidas;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

}
