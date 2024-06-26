package com.project.backend.dto;

public class SignUpRequestDTO {
    private Long id;
    private String email;
    private String password;
    private ClienteDTO cliente;

    public SignUpRequestDTO() {
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }
    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }
}
