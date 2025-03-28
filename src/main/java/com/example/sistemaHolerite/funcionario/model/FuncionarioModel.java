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

    private Integer dependentes;

    private Boolean temValeTransporte;

    private Double salarioBruto;

    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SalarioModel> salarios;


}
