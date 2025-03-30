package com.example.sistemaHolerite.holerite.model;

import com.example.sistemaHolerite.funcionario.model.FuncionarioModel;
import com.example.sistemaHolerite.salario.model.SalarioModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_holerite")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HoleriteModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private FuncionarioModel funcionarioModel;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private SalarioModel salarioModel;

    public HoleriteModel(FuncionarioModel funcionario, SalarioModel salario) {
        this.funcionarioModel = funcionario;
        this.salarioModel = salario;
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
}
