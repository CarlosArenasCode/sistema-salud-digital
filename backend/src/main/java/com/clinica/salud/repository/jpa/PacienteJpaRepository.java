package com.clinica.salud.repository.jpa;

import com.clinica.salud.entity.PacienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteJpaRepository extends JpaRepository<PacienteEntity, Long> {
}
