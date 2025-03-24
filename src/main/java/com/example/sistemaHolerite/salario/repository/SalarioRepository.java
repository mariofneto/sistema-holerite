package com.example.sistemaHolerite.salario.repository;

import com.example.sistemaHolerite.salario.model.SalarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalarioRepository extends JpaRepository<SalarioModel, Long> {
}
