package com.project.backend.model;

import jakarta.persistence.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome obrigatório!")
    @Column(name = "nome")
    private String nome;
    @NotBlank(message = "CPF obrigatório!")
    @Column(name = "cpf")
    private String cpf;
    @Column(name = "endereco")
    private String endereco;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ContaBancaria> contas = new ArrayList<>();

    public void initializeContas() {
        if (contas == null) {
            contas = new ArrayList<>();
        }
    }

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

    public void adicionarConta(ContaBancaria conta) {
        conta.setCliente(this);
        this.contas.add(conta);
    }
}
