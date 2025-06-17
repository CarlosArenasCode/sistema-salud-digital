package com.clinica.salud.dto;

import java.time.LocalDate;

/**
 * DTO para transferencia de datos de historiales médicos
 */
public class MedicalRecordDTO {

    /* ================================================================
     * ATRIBUTOS
     * Propiedades que representan los datos del historial médico
     * ================================================================ */
    private Integer id;
    private Integer pacienteId;
    private String nombrePaciente;
    private Integer medicoId;
    private String nombreMedico;
    private String especialidad;
    private LocalDate fechaConsulta;
    private String diagnostico;
    private String tratamiento;
    private String notas;
    
    /* ================================================================
     * CONSTRUCTORES
     * Métodos inicializadores de la clase
     * ================================================================ */
    public MedicalRecordDTO() {}
    
    public MedicalRecordDTO(Integer id, Integer pacienteId, String nombrePaciente, Integer medicoId, 
                         String nombreMedico, String especialidad, LocalDate fechaConsulta, 
                         String diagnostico, String tratamiento, String notas) {
        this.id = id;
        this.pacienteId = pacienteId;
        this.nombrePaciente = nombrePaciente;
        this.medicoId = medicoId;
        this.nombreMedico = nombreMedico;
        this.especialidad = especialidad;
        this.fechaConsulta = fechaConsulta;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.notas = notas;
    }

    /* ================================================================
     * MÉTODOS DE ACCESO
     * Getters y setters para acceder y modificar los atributos
     * ================================================================ */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Integer pacienteId) {
        this.pacienteId = pacienteId;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public Integer getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(Integer medicoId) {
        this.medicoId = medicoId;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public void setNombreMedico(String nombreMedico) {
        this.nombreMedico = nombreMedico;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public LocalDate getFechaConsulta() {
        return fechaConsulta;
    }

    public void setFechaConsulta(LocalDate fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    /* ================================================================
     * MÉTODOS SOBRESCRITOS
     * Implementaciones personalizadas de métodos heredados de Object
     * ================================================================ */
    @Override
    public String toString() {
        return "MedicalRecordDTO{" +
                "id=" + id +
                ", pacienteId=" + pacienteId +
                ", nombrePaciente='" + nombrePaciente + '\'' +
                ", medicoId=" + medicoId +
                ", nombreMedico='" + nombreMedico + '\'' +
                ", fechaConsulta=" + fechaConsulta +
                ", diagnostico='" + diagnostico + '\'' +
                '}';
    }
}
