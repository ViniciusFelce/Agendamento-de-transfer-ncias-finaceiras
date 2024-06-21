package com.project.backend.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "endereco")
    private String endereco;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "cliente_conta",
            joinColumns = @JoinColumn(name = "cliente_id"),
            inverseJoinColumns = @JoinColumn(name = "conta_id"))
    private List<ContaBancaria> contas;

    public Cliente(){
    }

    public Cliente(Long id, String nome, String cpf, String endereco, List<ContaBancaria> contas) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.contas = contas;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public List<ContaBancaria> getContas() {
        return contas;
    }
    public void setContas(List<ContaBancaria> contas) {
        this.contas = contas;
    }
}
