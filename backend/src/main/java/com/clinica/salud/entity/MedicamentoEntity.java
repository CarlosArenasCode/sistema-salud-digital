package com.clinica.salud.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad que representa un medicamento en el sistema de salud digital.
 * Mapea con la tabla 'medicamentos' en PostgreSQL.
 * Unificada y estandarizada en español.
 */
@Entity
@Table(name = "medicamentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class MedicamentoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 255, message = "El nombre no puede exceder 255 caracteres")
    @Column(name = "nombre", nullable = false)
    private String nombre;
    
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    
    @Size(max = 50, message = "El código no puede exceder 50 caracteres")
    @Column(name = "codigo", unique = true, length = 50)
    private String codigo;
    
    @Size(max = 255, message = "El fabricante no puede exceder 255 caracteres")
    @Column(name = "fabricante")
    private String fabricante;
    
    @Size(max = 100, message = "La categoría no puede exceder 100 caracteres")
    @Column(name = "categoria", length = 100)
    private String categoria;
    
    @Size(max = 50, message = "La forma de dosificación no puede exceder 50 caracteres")
    @Column(name = "forma_dosificacion", length = 50)
    private String formaDosificacion;
    
    @Size(max = 100, message = "La concentración no puede exceder 100 caracteres")
    @Column(name = "concentracion", length = 100)
    private String concentracion;
    
    @DecimalMin(value = "0.0", message = "El precio no puede ser negativo")
    @Column(name = "precio", precision = 10, scale = 2)
    private BigDecimal precio;
    
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Builder.Default
    @Column(name = "stock")
    private Integer stock = 0;
    
    @Min(value = 0, message = "El stock mínimo no puede ser negativo")
    @Builder.Default
    @Column(name = "stock_minimo")
    private Integer stockMinimo = 10;
    
    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;
    
    @Column(name = "instrucciones", columnDefinition = "TEXT")
    private String instrucciones;
    
    @Column(name = "efectos_secundarios", columnDefinition = "TEXT")
    private String efectosSecundarios;
      @Column(name = "contraindicaciones", columnDefinition = "TEXT")
    private String contraindicaciones;
    
    @Builder.Default
    @Column(name = "requiere_receta")
    private Boolean requiereReceta = false;
    
    @Builder.Default
    @Column(name = "activo")
    private Boolean activo = true;
    
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
    
    // Métodos de negocio
    public boolean isVencido() {
        return fechaVencimiento != null && fechaVencimiento.isBefore(LocalDate.now());
    }
    
    public boolean necesitaRestock() {
        return stock != null && stockMinimo != null && stock <= stockMinimo;
    }
    
    public boolean isDisponible() {
        return activo && !isVencido() && stock != null && stock > 0;
    }
}
