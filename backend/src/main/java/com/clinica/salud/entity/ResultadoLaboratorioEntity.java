package com.clinica.salud.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad JPA para resultados de laboratorio
 * Mapea con la tabla 'resultados_laboratorio' en PostgreSQL
 */
@Entity
@Table(name = "resultados_laboratorio")
public class ResultadoLaboratorioEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "El ID del paciente es obligatorio")
    @Column(name = "id_paciente", nullable = false)
    private Long idPaciente;
    
    @Column(name = "id_medico")
    private Long idMedico;
    
    @NotBlank(message = "El nombre del examen es obligatorio")
    @Size(max = 100, message = "El nombre del examen no puede exceder 100 caracteres")
    @Column(name = "nombre_examen", nullable = false, length = 100)
    private String nombreExamen;
    
    @NotNull(message = "La fecha del examen es obligatoria")
    @Column(name = "fecha_examen", nullable = false)
    private LocalDate fechaExamen;
    
    @Column(name = "resultados", columnDefinition = "TEXT")
    private String resultados;
    
    @Column(name = "rango_normal", columnDefinition = "TEXT")
    private String rangoNormal;
    
    @Column(name = "es_normal")
    private Boolean esNormal;
    
    @Column(name = "notas", columnDefinition = "TEXT")
    private String notas;
    
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
    
    // Relaciones JPA
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paciente", insertable = false, updatable = false)
    private PacienteEntity paciente;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_medico", insertable = false, updatable = false)
    private MedicoEntity medico;
    
    // Constructor por defecto
    public ResultadoLaboratorioEntity() {}
    
    // Constructor con campos básicos
    public ResultadoLaboratorioEntity(Long idPaciente, String nombreExamen, LocalDate fechaExamen) {
        this.idPaciente = idPaciente;
        this.nombreExamen = nombreExamen;
        this.fechaExamen = fechaExamen;
    }
    
    // Métodos de auditoría
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
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
    
    public String getNombreExamen() {
        return nombreExamen;
    }
    
    public void setNombreExamen(String nombreExamen) {
        this.nombreExamen = nombreExamen;
    }
    
    public LocalDate getFechaExamen() {
        return fechaExamen;
    }
    
    public void setFechaExamen(LocalDate fechaExamen) {
        this.fechaExamen = fechaExamen;
    }
    
    public String getResultados() {
        return resultados;
    }
    
    public void setResultados(String resultados) {
        this.resultados = resultados;
    }
    
    public String getRangoNormal() {
        return rangoNormal;
    }
    
    public void setRangoNormal(String rangoNormal) {
        this.rangoNormal = rangoNormal;
    }
    
    public Boolean getEsNormal() {
        return esNormal;
    }
    
    public void setEsNormal(Boolean esNormal) {
        this.esNormal = esNormal;
    }
    
    public String getNotas() {
        return notas;
    }
    
    public void setNotas(String notas) {
        this.notas = notas;
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
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
    
    @Override
    public String toString() {
        return "ResultadoLaboratorioEntity{" +
                "id=" + id +
                ", idPaciente=" + idPaciente +
                ", nombreExamen='" + nombreExamen + '\'' +
                ", fechaExamen=" + fechaExamen +
                ", esNormal=" + esNormal +
                '}';
    }
}
