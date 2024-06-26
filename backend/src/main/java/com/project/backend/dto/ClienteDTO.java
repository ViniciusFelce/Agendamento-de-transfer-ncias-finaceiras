package com.project.backend.dto;

import java.util.List;

public class ClienteDTO {
    private Long id;
    private String nome;
    private String cpf;
    private String endereco;
    private List<ContaBancariaDTO> contas;

    public ClienteDTO() {
    }

    public ClienteDTO(Long id, String nome, String cpf, String endereco, List<ContaBancariaDTO> contas) {
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

    public List<ContaBancariaDTO> getContas() {
        return contas;
    }

    public void setContas(List<ContaBancariaDTO> contas) {
        this.contas = contas;
    }
}
