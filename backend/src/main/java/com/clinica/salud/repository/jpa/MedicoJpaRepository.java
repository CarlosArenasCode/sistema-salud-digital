package com.clinica.salud.repository.jpa;

import com.clinica.salud.entity.MedicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicoJpaRepository extends JpaRepository<MedicoEntity, Long> {
}
