package com.example.sistemaHolerite.holerite.repository;

import com.example.sistemaHolerite.holerite.model.HoleriteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoleriteRepository extends JpaRepository<HoleriteModel, Long> {
}
