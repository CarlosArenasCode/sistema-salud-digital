package com.clinica.salud.entity;

// Enum que define los roles de usuario en el sistema de salud digital
public enum RolUsuario {
    ADMINISTRADOR,  // Acceso total al sistema
    RECEPCIONISTA,  // Gestiona citas y recepción
    MEDICO,         // Atiende pacientes y gestiona historias clínicas
    PACIENTE        // Recibe atención médica
}
