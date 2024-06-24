package com.project.backend.model;

import jakarta.persistence.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome obrigatório!")
    @Column(name = "nome")
    private String nome;
    @NotBlank(message = "CPF obrigatório!")
    @Column(name = "cpf")
    private String cpf;

    @NotBlank(message = "E-mail obrigatório!")
    @Email(message = "E-mail inválido!")
    @Column(unique = true)
    private String username; // Username = Email

    @NotBlank(message = "Senha obrigatório!")
    private String password; // Criptografado

    @Column(name = "endereco")
    private String endereco;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ContaBancaria> contas = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "usuario_papeis",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "papeis_id"))
    private Set<Role> roles = new HashSet<>();

    public void initializeContas() {
        if (contas == null) {
            contas = new ArrayList<>();
        }
    }

    public Cliente(){
    }

    public Cliente(Long id, String nome, String cpf, String username, String password, String endereco, List<ContaBancaria> contas) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.username = username;
        this.password = password;
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

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void adicionarConta(ContaBancaria conta) {
        conta.setCliente(this);
        this.contas.add(conta);
    }
}
