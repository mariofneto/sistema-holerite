package com.example.sistemaHolerite.rh.model;

import com.example.sistemaHolerite.roles.utils.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "tb_rh")
public class RhModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome não pode ser vazio")
    @Column(unique = true)
    private String nome;

    @NotBlank(message = "Senha não pode ser vazia")
    private String senha;

    @NotBlank(message = "Email não pode ser vazio")
    @Column(unique = true)
    private String email;

    public RhModel() {
        this.role = Role.RH_ADMIN;
    }

    public RhModel(Long id, String nome, String senha, String email, Role role) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.email = email;
        this.role = role;
    }

    private Role role;

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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }
}
