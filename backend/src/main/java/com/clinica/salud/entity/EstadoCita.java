package com.clinica.salud.entity;

// Enumeración que define los estados posibles de una cita médica
public enum EstadoCita {
    // Cita registrada pero sin fecha y hora asignada
    PENDIENTE,
    // Cita con fecha y hora asignada
    PROGRAMADA,
    // Cita confirmada por el paciente
    CONFIRMADA,
    
    // Cita que se llevó a cabo correctamente
    COMPLETADA,
    // Cita cancelada con anticipación
    CANCELADA,
    // Paciente no se presentó a la cita
    NO_ASISTIO
}
