package com.example.sistemaHolerite.funcionario.repository;

import com.example.sistemaHolerite.funcionario.model.FuncionarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<FuncionarioModel, Long> {
    Optional<FuncionarioModel> findByNomeAndSenha(String nome, String senha);
}
