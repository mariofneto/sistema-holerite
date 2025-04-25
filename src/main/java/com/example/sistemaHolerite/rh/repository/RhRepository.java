package com.example.sistemaHolerite.rh.repository;

import com.example.sistemaHolerite.rh.model.RhModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RhRepository extends JpaRepository<RhModel, Long> {
    Optional<RhModel> findByNomeAndSenha(String nome, String senha);
    Optional<RhModel> findByNome(String nome);
}
