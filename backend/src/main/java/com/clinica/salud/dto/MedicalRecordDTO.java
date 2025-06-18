package com.clinica.salud.dto;

import java.time.LocalDate;

// DTO para transferencia de datos de historiales médicos
public class MedicalRecordDTO {    // Identificador único del historial médico
    private Integer id;
    // ID del paciente asociado
    private Integer pacienteId;
    // Nombre completo del paciente
    private String nombrePaciente;
    // ID del médico tratante
    private Integer medicoId;
    // Nombre completo del médico
    private String nombreMedico;
    // Especialidad médica del doctor
    private String especialidad;
    // Fecha de la consulta médica
    private LocalDate fechaConsulta;
    // Diagnóstico realizado por el médico
    private String diagnostico;
    // Tratamiento prescrito al paciente
    private String tratamiento;
    // Notas adicionales del historial
    private String notas;    
    // Constructor por defecto
    public MedicalRecordDTO() {}
    
    // Constructor completo con todos los parámetros
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
        this.notas = notas;    }

    // Getter para obtener el ID del historial
    public Integer getId() {
        return id;    }

    // Setter para establecer el ID del historial
    public void setId(Integer id) {
        this.id = id;
    }

    // Getter para obtener el ID del paciente
    public Integer getPacienteId() {
        return pacienteId;
    }

    // Setter para establecer el ID del paciente
    public void setPacienteId(Integer pacienteId) {
        this.pacienteId = pacienteId;
    }

    // Getter para obtener el nombre del paciente
    public String getNombrePaciente() {
        return nombrePaciente;
    }

    // Setter para establecer el nombre del paciente
    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    // Getter para obtener el ID del médico
    public Integer getMedicoId() {
        return medicoId;
    }

    // Setter para establecer el ID del médico
    public void setMedicoId(Integer medicoId) {
        this.medicoId = medicoId;
    }

    // Getter para obtener el nombre del médico
    public String getNombreMedico() {
        return nombreMedico;
    }

    // Setter para establecer el nombre del médico
    public void setNombreMedico(String nombreMedico) {
        this.nombreMedico = nombreMedico;
    }

    // Getter para obtener la especialidad médica
    public String getEspecialidad() {
        return especialidad;
    }

    // Setter para establecer la especialidad médica
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    // Getter para obtener la fecha de consulta
    public LocalDate getFechaConsulta() {
        return fechaConsulta;
    }

    // Setter para establecer la fecha de consulta
    public void setFechaConsulta(LocalDate fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }

    // Getter para obtener el diagnóstico
    public String getDiagnostico() {
        return diagnostico;
    }

    // Setter para establecer el diagnóstico
    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    // Getter para obtener el tratamiento
    public String getTratamiento() {
        return tratamiento;
    }

    // Setter para establecer el tratamiento
    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    // Getter para obtener las notas adicionales
    public String getNotas() {
        return notas;
    }

    // Setter para establecer las notas adicionales
    public void setNotas(String notas) {
        this.notas = notas;    }

    // Método toString para representación textual del objeto
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
