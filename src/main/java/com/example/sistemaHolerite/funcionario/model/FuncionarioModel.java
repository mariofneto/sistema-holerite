package com.example.sistemaHolerite.funcionario.model;

import com.example.sistemaHolerite.salario.model.SalarioModel;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "tb_funcionario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class FuncionarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String senha;

    private Integer dependentes;

    private Boolean temValeTransporte;

    private Double salarioBruto;

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

    public List<SalarioModel> getSalarios() {
        return salarios;
    }

    public void setSalarios(List<SalarioModel> salarios) {
        this.salarios = salarios;
    }
}
