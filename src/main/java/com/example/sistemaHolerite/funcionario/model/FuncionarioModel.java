package com.example.sistemaHolerite.funcionario.model;

import com.example.sistemaHolerite.roles.utils.Role;
import com.example.sistemaHolerite.salario.model.SalarioModel;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "tb_funcionario")

public class FuncionarioModel {

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

    @Min(value = 0, message = "Dependentes não pode ser vazio")
    private Integer dependentes;

    private Boolean temValeTransporte;

    @NotNull(message = "Salario Bruto não pode ser vazio")
    private Double salarioBruto;

    private Role role;

    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SalarioModel> salarios;

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

    public String getEmail(){return email;}

    public void setEmail(String email){this.email = email;}

    public Integer getDependentes() {
        return dependentes;
    }

    public void setDependentes(Integer dependentes) {
        this.dependentes = dependentes;
    }

    public Boolean getTemValeTransporte() {
        return temValeTransporte;
    }

    public void setTemValeTransporte(Boolean temValeTransporte) {
        this.temValeTransporte = temValeTransporte;
    }

    public Double getSalarioBruto() {
        return salarioBruto;
    }

    public void setSalarioBruto(Double salarioBruto) {
        this.salarioBruto = salarioBruto;
    }

    public Role getRole(){
        return role;
    }

    public void setRole(){
        this.role = Role.FUNCIONARIO;
    }

    public List<SalarioModel> getSalarios() {
        return salarios;
    }

    public void setSalarios(List<SalarioModel> salarios) {
        this.salarios = salarios;
    }
}
