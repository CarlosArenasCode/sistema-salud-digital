package com.clinica.salud.entity;

/**
 * Enumeración que representa los diferentes estados posibles de una cita médica
 */
public enum EstadoCita {
    // Estados previos a la atención
    PENDIENTE,   // Cita registrada pero sin fecha y hora asignada
    PROGRAMADA,  // Cita con fecha y hora asignada
    CONFIRMADA,  // Cita confirmada por el paciente
    
    // Estados de finalización
    COMPLETADA,  // Cita que se llevó a cabo correctamente
    CANCELADA,   // Cita cancelada con anticipación
    NO_ASISTIO   // Paciente no se presentó a la cita
}
