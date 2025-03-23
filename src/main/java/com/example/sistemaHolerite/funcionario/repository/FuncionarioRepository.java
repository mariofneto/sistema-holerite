package com.example.sistemaHolerite.funcionario.repository;

import com.example.sistemaHolerite.funcionario.model.FuncionarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends JpaRepository<FuncionarioModel, Long> {
}
