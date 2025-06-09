package com.clinica.salud.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad que representa el historial médico de un paciente.
 * Mapea con la tabla 'historial_medico' en PostgreSQL.
 * 
 * Versión corregida: Todos los campos en español según análisis de BD
 */
@Entity
@Table(name = "historial_medico")
public class HistorialMedicoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @NotNull(message = "El ID del paciente es obligatorio")
    @Column(name = "id_paciente", nullable = false)
    private Long idPaciente;
    
    @NotNull(message = "El ID del médico es obligatorio")
    @Column(name = "id_medico", nullable = false)
    private Long idMedico;
    
    @NotNull(message = "La fecha de visita es obligatoria")
    @Column(name = "fecha_visita", nullable = false)
    private LocalDate fechaVisita;
    
    @Column(name = "motivo_consulta", columnDefinition = "TEXT")
    private String motivoConsulta;
    
    @Column(name = "sintomas", columnDefinition = "TEXT")
    private String sintomas;
    
    @Column(name = "diagnostico", columnDefinition = "TEXT")
    private String diagnostico;
    
    @Column(name = "tratamiento", columnDefinition = "TEXT")
    private String tratamiento;
    
    // Campos corregidos de inglés a español
    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones; // Era "observations"
    
    @Column(name = "medicamentos", columnDefinition = "TEXT")
    private String medicamentos; // Era "medications"
    
    @Column(name = "examen_fisico", columnDefinition = "TEXT")
    private String examenFisico; // Era "examinations"
    
    @Column(name = "recomendaciones", columnDefinition = "TEXT")
    private String recomendaciones; // Era "follow_up_instructions"
    
    @Column(name = "proxima_cita")
    private LocalDate proximaCita;
    
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    // Relaciones JPA (opcional, para facilitar consultas)
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paciente", insertable = false, updatable = false)
    private PacienteEntity paciente;
    
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_medico", insertable = false, updatable = false)
    private MedicoEntity medico;
    
    // Constructor por defecto
    public HistorialMedicoEntity() {}
    
    // Constructor con campos principales
    public HistorialMedicoEntity(Long idPaciente, Long idMedico, LocalDate fechaVisita, 
                                String motivoConsulta, String sintomas, String diagnostico, 
                                String tratamiento) {
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.fechaVisita = fechaVisita;
        this.motivoConsulta = motivoConsulta;
        this.sintomas = sintomas;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
    }
    
    // Constructor completo
    public HistorialMedicoEntity(Long idPaciente, Long idMedico, LocalDate fechaVisita, 
                                String motivoConsulta, String sintomas, String diagnostico, 
                                String tratamiento, String observaciones, String medicamentos,
                                String examenFisico, String recomendaciones, LocalDate proximaCita) {
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.fechaVisita = fechaVisita;
        this.motivoConsulta = motivoConsulta;
        this.sintomas = sintomas;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.observaciones = observaciones;
        this.medicamentos = medicamentos;
        this.examenFisico = examenFisico;
        this.recomendaciones = recomendaciones;
        this.proximaCita = proximaCita;
    }
    
    // Callbacks JPA para timestamps
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getIdPaciente() {
        return idPaciente;
    }
    
    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }
    
    public Long getIdMedico() {
        return idMedico;
    }
    
    public void setIdMedico(Long idMedico) {
        this.idMedico = idMedico;
    }
    
    public LocalDate getFechaVisita() {
        return fechaVisita;
    }
    
    public void setFechaVisita(LocalDate fechaVisita) {
        this.fechaVisita = fechaVisita;
    }
    
    public String getMotivoConsulta() {
        return motivoConsulta;
    }
    
    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }
    
    public String getSintomas() {
        return sintomas;
    }
    
    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
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
    
    public String getObservaciones() {
        return observaciones;
    }
    
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    public String getMedicamentos() {
        return medicamentos;
    }
    
    public void setMedicamentos(String medicamentos) {
        this.medicamentos = medicamentos;
    }
    
    public String getExamenFisico() {
        return examenFisico;
    }
    
    public void setExamenFisico(String examenFisico) {
        this.examenFisico = examenFisico;
    }
    
    public String getRecomendaciones() {
        return recomendaciones;
    }
    
    public void setRecomendaciones(String recomendaciones) {
        this.recomendaciones = recomendaciones;
    }
    
    public LocalDate getProximaCita() {
        return proximaCita;
    }
    
    public void setProximaCita(LocalDate proximaCita) {
        this.proximaCita = proximaCita;
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }
    
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
    
    public PacienteEntity getPaciente() {
        return paciente;
    }
    
    public void setPaciente(PacienteEntity paciente) {
        this.paciente = paciente;
    }
    
    public MedicoEntity getMedico() {
        return medico;
    }
    
    public void setMedico(MedicoEntity medico) {
        this.medico = medico;
    }
    
    // Métodos de compatibilidad para transición (deprecados)
    @Deprecated
    public String getObservations() {
        return this.observaciones;
    }
    
    @Deprecated
    public void setObservations(String observations) {
        this.observaciones = observations;
    }
    
    @Deprecated
    public String getMedications() {
        return this.medicamentos;
    }
    
    @Deprecated
    public void setMedications(String medications) {
        this.medicamentos = medications;
    }
    
    @Deprecated
    public String getExaminations() {
        return this.examenFisico;
    }
    
    @Deprecated
    public void setExaminations(String examinations) {
        this.examenFisico = examinations;
    }
    
    @Deprecated
    public String getFollowUpInstructions() {
        return this.recomendaciones;
    }
    
    @Deprecated
    public void setFollowUpInstructions(String followUpInstructions) {
        this.recomendaciones = followUpInstructions;
    }
    
    @Deprecated
    public LocalDate getVisitDate() {
        return this.fechaVisita;
    }
    
    @Deprecated
    public void setVisitDate(LocalDate visitDate) {
        this.fechaVisita = visitDate;
    }
    
    @Override
    public String toString() {
        return "HistorialMedicoEntity{" +
                "id=" + id +
                ", idPaciente=" + idPaciente +
                ", idMedico=" + idMedico +
                ", fechaVisita=" + fechaVisita +
                ", motivoConsulta='" + motivoConsulta + '\'' +
                ", diagnostico='" + diagnostico + '\'' +
                '}';
    }
}
