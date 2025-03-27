package com.example.sistemaHolerite.salario.model;

import com.example.sistemaHolerite.funcionario.model.FuncionarioModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_salario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalarioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataSalario;

    private Double descontoInss;

    private Double descontoIrrf;

    private Double descontoValeTransporte;

    private Double salarioLiquido;

    @ManyToOne
    @JoinColumn(name = "funcionario_id", nullable = false)
    @JsonBackReference
    private FuncionarioModel funcionario;

    public SalarioModel(LocalDateTime dataSalario, Double descontoInss, Double descontoIrrf, Double valeTransporte, Double salarioLiquido, FuncionarioModel funcionario){
        this.dataSalario = dataSalario;
        this.descontoInss = descontoInss;
        this.descontoIrrf = descontoIrrf;
        this.descontoValeTransporte = valeTransporte;
        this.salarioLiquido = salarioLiquido;
        this.funcionario = funcionario;
    }
}
