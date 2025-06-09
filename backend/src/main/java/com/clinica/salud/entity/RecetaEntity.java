package com.clinica.salud.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * Entidad JPA para recetas médicas
 * Mapea con la tabla 'recetas' en PostgreSQL
 */
@Entity
@Table(name = "recetas")
public class RecetaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "id_historial_medico")
    private Long idHistorialMedico;
    
    @Column(name = "id_medicamento")
    private Long idMedicamento;
    
    @NotBlank(message = "La dosis es obligatoria")
    @Size(max = 50, message = "La dosis no puede exceder 50 caracteres")
    @Column(name = "dosis", nullable = false, length = 50)
    private String dosis;
    
    @NotBlank(message = "La frecuencia es obligatoria")
    @Size(max = 50, message = "La frecuencia no puede exceder 50 caracteres")
    @Column(name = "frecuencia", nullable = false, length = 50)
    private String frecuencia;
    
    @Size(max = 50, message = "La duración no puede exceder 50 caracteres")
    @Column(name = "duracion", length = 50)
    private String duracion;
    
    @Column(name = "notas", columnDefinition = "TEXT")
    private String notas;
    
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
    
    // Relaciones JPA
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_historial_medico", insertable = false, updatable = false)
    private HistorialMedicoEntity historialMedico;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_medicamento", insertable = false, updatable = false)
    private MedicamentoEntity medicamento;
    
    // Constructor por defecto
    public RecetaEntity() {}
    
    // Constructor con campos básicos
    public RecetaEntity(Long idHistorialMedico, Long idMedicamento, String dosis, String frecuencia) {
        this.idHistorialMedico = idHistorialMedico;
        this.idMedicamento = idMedicamento;
        this.dosis = dosis;
        this.frecuencia = frecuencia;
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
    
    public Long getIdHistorialMedico() {
        return idHistorialMedico;
    }
    
    public void setIdHistorialMedico(Long idHistorialMedico) {
        this.idHistorialMedico = idHistorialMedico;
    }
    
    public Long getIdMedicamento() {
        return idMedicamento;
    }
    
    public void setIdMedicamento(Long idMedicamento) {
        this.idMedicamento = idMedicamento;
    }
    
    public String getDosis() {
        return dosis;
    }
    
    public void setDosis(String dosis) {
        this.dosis = dosis;
    }
    
    public String getFrecuencia() {
        return frecuencia;
    }
    
    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }
    
    public String getDuracion() {
        return duracion;
    }
    
    public void setDuracion(String duracion) {
        this.duracion = duracion;
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
    
    public HistorialMedicoEntity getHistorialMedico() {
        return historialMedico;
    }
    
    public void setHistorialMedico(HistorialMedicoEntity historialMedico) {
        this.historialMedico = historialMedico;
    }
    
    public MedicamentoEntity getMedicamento() {
        return medicamento;
    }
    
    public void setMedicamento(MedicamentoEntity medicamento) {
        this.medicamento = medicamento;
    }
    
    @Override
    public String toString() {
        return "RecetaEntity{" +
                "id=" + id +
                ", idHistorialMedico=" + idHistorialMedico +
                ", idMedicamento=" + idMedicamento +
                ", dosis='" + dosis + '\'' +
                ", frecuencia='" + frecuencia + '\'' +
                ", duracion='" + duracion + '\'' +
                '}';
    }
}
