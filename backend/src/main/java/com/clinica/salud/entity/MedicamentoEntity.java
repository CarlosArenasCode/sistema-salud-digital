package com.clinica.salud.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

// Entidad que representa un medicamento en el sistema, mapeada a la tabla 'medicamentos'
@Entity
@Table(name = "medicamentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class MedicamentoEntity {
    
    // Identificador único del medicamento
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Nombre comercial del medicamento
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 255, message = "El nombre no puede exceder 255 caracteres")
    @Column(name = "nombre", nullable = false)
    private String nombre;
    
    // Información detallada sobre el medicamento
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    
    // Código único de identificación farmacéutica
    @Size(max = 50, message = "El código no puede exceder 50 caracteres")
    @Column(name = "codigo", unique = true, length = 50)
    private String codigo;
    
    // Empresa que produce el medicamento
    @Size(max = 255, message = "El fabricante no puede exceder 255 caracteres")
    @Column(name = "fabricante")
    private String fabricante;
    
    // Clasificación terapéutica del medicamento
    @Size(max = 100, message = "La categoría no puede exceder 100 caracteres")
    @Column(name = "categoria", length = 100)
    private String categoria;
    
    // Forma de administración (tableta, jarabe, inyectable, etc.)
    @Size(max = 50, message = "La forma de dosificación no puede exceder 50 caracteres")
    @Column(name = "forma_dosificacion", length = 50)
    private String formaDosificacion;
    
    // Cantidad de principio activo por unidad
    @Size(max = 100, message = "La concentración no puede exceder 100 caracteres")
    @Column(name = "concentracion", length = 100)
    private String concentracion;
    
    // Valor monetario del medicamento
    @DecimalMin(value = "0.0", message = "El precio no puede ser negativo")
    @Column(name = "precio", precision = 10, scale = 2)
    private BigDecimal precio;
    
    // Cantidad disponible en inventario
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Builder.Default
    @Column(name = "stock")
    private Integer stock = 0;
    
    // Nivel mínimo antes de considerar reabastecimiento
    @Min(value = 0, message = "El stock mínimo no puede ser negativo")
    @Builder.Default
    @Column(name = "stock_minimo")
    private Integer stockMinimo = 10;
    
    // Fecha límite de uso seguro del medicamento
    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;
    
    // Directrices para el uso adecuado del medicamento
    @Column(name = "instrucciones", columnDefinition = "TEXT")
    private String instrucciones;
    
    // Reacciones adversas posibles
    @Column(name = "efectos_secundarios", columnDefinition = "TEXT")
    private String efectosSecundarios;
    
    // Condiciones en las que no debe usarse el medicamento
    @Column(name = "contraindicaciones", columnDefinition = "TEXT")
    private String contraindicaciones;
    
    // Indica si necesita prescripción médica para dispensarse
    @Builder.Default
    @Column(name = "requiere_receta")
    private Boolean requiereReceta = false;
    
    // Indica si el medicamento está disponible para uso
    @Builder.Default
    @Column(name = "activo")
    private Boolean activo = true;
    
    // Relación con las recetas que incluyen este medicamento
    @OneToMany(mappedBy = "medicamento", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<RecetaEntity> recetas;
    
    // Momento en que se registró el medicamento en el sistema
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
    
    // Última modificación de los datos del medicamento
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    // Inicializa fechas al crear un nuevo registro
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
    }
    
    // Actualiza la fecha de modificación al guardar cambios
    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
    
    // Verifica si el medicamento ha expirado según su fecha de vencimiento
    public boolean isVencido() {
        return fechaVencimiento != null && fechaVencimiento.isBefore(LocalDate.now());
    }
    
    // Determina si el nivel de inventario está bajo el mínimo requerido
    public boolean necesitaRestock() {
        return stock != null && stockMinimo != null && stock <= stockMinimo;
    }
    
    // Verifica si el medicamento puede ser dispensado (activo, no vencido y con stock)
    public boolean isDisponible() {
        return activo && !isVencido() && stock != null && stock > 0;
    }
}
