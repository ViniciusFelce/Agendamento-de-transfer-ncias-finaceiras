package com.project.backend.dto;

import java.math.BigDecimal;
import java.util.List;

public class ClienteDTO {
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private String endereco;
    private List<ContaBancariaDTO> contas;

    public ClienteDTO() {
    }

    public ClienteDTO(Long id, String nome, String cpf, String email, String senha, String endereco, List<ContaBancariaDTO> contas) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public List<ContaBancariaDTO> getContas() {
        return contas;
    }

    public void setContas(List<ContaBancariaDTO> contas) {
        this.contas = contas;
    }
}
