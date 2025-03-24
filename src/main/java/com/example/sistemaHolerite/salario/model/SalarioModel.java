package com.example.sistemaHolerite.salario.model;

import com.example.sistemaHolerite.funcionario.model.FuncionarioModel;
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

    private Double salarioBruto;

    private Double descontoInss;

    private Double descontoIrrf;

    private Boolean valeTransporte;

    private Double salarioLiquido;

    @ManyToOne
    @JoinColumn(name = "funcionario_id", nullable = false)
    private FuncionarioModel funcionario;

}
