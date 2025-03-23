package com.example.sistemaHolerite.salario.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private Double salarioBruto;

    private Double descontoInss;

    private Double descontoIrrf;

    private Boolean valeTransporte;

    private Double salarioLiquido;

}
