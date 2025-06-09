package com.clinica.salud.repository;

// Disabled old repository - now using MedicamentoJpaRepository with MedicamentoEntity
/*
import com.clinica.salud.modelo.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {
    List<Medicamento> findByNameContaining(String name); // Método corregido previamente
}
*/