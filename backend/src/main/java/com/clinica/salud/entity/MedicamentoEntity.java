package com.clinica.salud.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad que representa un medicamento en el sistema de salud digital.
 * Mapea con la tabla 'medicamentos' en PostgreSQL.
 * OPTIMIZADA: Reducido de 446 líneas a ~100 líneas usando Lombok y BaseEntity
 */
@Entity
@Table(name = "medicamentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class MedicamentoEntity extends BaseEntity {
    
    @NotBlank(message = "El nombre del medicamento es obligatorio")
    @Size(max = 255, message = "El nombre no puede exceder 255 caracteres")
    @Column(name = "nombre", nullable = false)
    private String nombre;
    
    @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
    @Column(name = "descripcion")
    private String descripcion;
    
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.00", message = "El precio debe ser mayor or igual a 0")
    @Column(name = "precio", precision = 10, scale = 2)
    private BigDecimal precio;
    
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Builder.Default
    @Column(name = "stock")
    private Integer stock = 0;
    
    @Size(max = 100, message = "El fabricante no puede exceder 100 caracteres")
    @Column(name = "fabricante")
    private String fabricante;
    
    @Size(max = 50, message = "La categoría no puede exceder 50 caracteres")
    @Column(name = "categoria")
    private String categoria;
    
    @Size(max = 50, message = "La forma farmacéutica no puede exceder 50 caracteres")
    @Column(name = "forma")
    private String forma;
    
    @Size(max = 100, message = "La concentración no puede exceder 100 caracteres")
    @Column(name = "concentracion")
    private String concentracion;
    
    @Builder.Default
    @Column(name = "requiere_receta")
    private Boolean requiereReceta = false;
    
    @Builder.Default
    @Column(name = "activo")
    private Boolean activo = true;
    
    @Size(max = 500, message = "Las instrucciones no pueden exceder 500 caracteres")
    @Column(name = "instrucciones")
    private String instrucciones;
    
    @Column(name = "fecha_vencimiento")
    private LocalDateTime fechaVencimiento;
    
    @NotBlank(message = "El código es obligatorio")
    @Size(max = 50, message = "El código no puede exceder 50 caracteres")
    @Column(name = "codigo", unique = true, nullable = false)
    private String codigo;
    
    @Min(value = 0, message = "El stock mínimo no puede ser negativo")
    @Builder.Default
    @Column(name = "stock_minimo")
    private Integer stockMinimo = 0;
    
    @Size(max = 200, message = "El lote no puede exceder 200 caracteres")
    @Column(name = "lote")
    private String lote;
    
    @Size(max = 100, message = "La presentación no puede exceder 100 caracteres")
    @Column(name = "presentacion")
    private String presentacion;
    
    @Size(max = 500, message = "Las contraindicaciones no pueden exceder 500 caracteres")
    @Column(name = "contraindicaciones")
    private String contraindicaciones;
    
    @Size(max = 500, message = "Los efectos secundarios no pueden exceder 500 caracteres")
    @Column(name = "efectos_secundarios")
    private String efectosSecundarios;
    
    @Size(max = 200, message = "Las condiciones de almacenamiento no pueden exceder 200 caracteres")
    @Column(name = "condiciones_almacenamiento")
    private String condicionesAlmacenamiento;
    
    // Métodos de negocio
    public boolean isVencido() {
        return fechaVencimiento != null && fechaVencimiento.isBefore(LocalDateTime.now());
    }
    
    public boolean necesitaRestock() {
        return stock != null && stockMinimo != null && stock <= stockMinimo;
    }
    
    public boolean isDisponible() {
        return activo && stock != null && stock > 0 && !isVencido();
    }
}
