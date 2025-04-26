package com.example.sistemaHolerite.holerite.model;

import com.example.sistemaHolerite.funcionario.model.FuncionarioModel;
import com.example.sistemaHolerite.salario.model.SalarioModel;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_holerite")
public class HoleriteModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private FuncionarioModel funcionarioModel;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private SalarioModel salarioModel;

    @Column(name = "salarioBruto")
    private Double salarioBrutoNaData;

    public HoleriteModel() {
    }

    public HoleriteModel(FuncionarioModel funcionario, SalarioModel salario) {
        this.funcionarioModel = funcionario;
        this.salarioModel = salario;
    }

    public HoleriteModel(FuncionarioModel funcionario, SalarioModel salario, Double salarioBrutoNaData) {
        this.funcionarioModel = funcionario;
        this.salarioModel = salario;
        this.salarioBrutoNaData = salarioBrutoNaData;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FuncionarioModel getFuncionarioModel() {
        return funcionarioModel;
    }

    public void setFuncionarioModel(FuncionarioModel funcionarioModel) {
        this.funcionarioModel = funcionarioModel;
    }

    public SalarioModel getSalarioModel() {
        return salarioModel;
    }

    public void setSalarioModel(SalarioModel salarioModel) {
        this.salarioModel = salarioModel;
    }

    public Double getSalarioBrutoNaData() {
        return salarioBrutoNaData;
    }

    public void setSalarioBrutoNaData(Double salarioBrutoNaData) {
        this.salarioBrutoNaData = salarioBrutoNaData;
    }
}
