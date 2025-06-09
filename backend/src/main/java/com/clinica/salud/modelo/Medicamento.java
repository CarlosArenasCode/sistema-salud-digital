package com.clinica.salud.modelo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Modelo/DTO que representa un medicamento.
 * Actualizado para coincidir con la estructura corregida de la base de datos.
 */
@Schema(description = "Entidad que representa un medicamento")
public class Medicamento {
    
    @Schema(description = "ID del medicamento")
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Schema(description = "Nombre del medicamento", example = "Paracetamol")
    private String nombre; // Corregido de "name"

    @Schema(description = "Descripción del medicamento", example = "Analgésico y antipirético")
    private String descripcion; // Corregido de "description"

    @Schema(description = "Fabricante del medicamento", example = "LabFarma S.A.")
    private String fabricante; // Corregido de "manufacturer"

    @Schema(description = "Categoría del medicamento", example = "ANALGESICO")
    private String categoria; // Nuevo campo

    @DecimalMin(value = "0.0", message = "El precio debe ser mayor o igual a 0")
    @Schema(description = "Precio del medicamento", example = "15.50")
    private BigDecimal precio; // Nuevo campo

    @Min(value = 0, message = "El stock no puede ser negativo")
    @Schema(description = "Stock disponible", example = "100")
    private Integer stock; // Nuevo campo

    @Schema(description = "Stock mínimo", example = "10")
    private Integer stockMinimo; // Nuevo campo

    @Schema(description = "Forma farmacéutica", example = "Tableta")
    private String forma; // Corregido de "form"

    @Schema(description = "Concentración del medicamento", example = "500mg")
    private String concentracion; // Nuevo campo

    @Schema(description = "Si requiere receta médica", example = "false")
    private Boolean requiereReceta; // Nuevo campo

    @Schema(description = "Si el medicamento está activo", example = "true")
    private Boolean activo; // Nuevo campo

    @Schema(description = "Instrucciones de uso", example = "Tomar 1 tableta cada 8 horas")
    private String instrucciones; // Nuevo campo

    @Schema(description = "Fecha de vencimiento")
    private LocalDateTime fechaVencimiento; // Nuevo campo

    @Schema(description = "Código único del medicamento", example = "MED001")
    private String codigo; // Nuevo campo

    // Constructor por defecto
    public Medicamento() {}

    // Constructor básico
    public Medicamento(Long id, String nombre, String descripcion, String fabricante) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fabricante = fabricante;
    }

    // Constructor completo
    public Medicamento(Long id, String nombre, String descripcion, String fabricante, 
                      String categoria, BigDecimal precio, Integer stock, Integer stockMinimo,
                      String forma, String concentracion, Boolean requiereReceta, 
                      Boolean activo, String instrucciones, String codigo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fabricante = fabricante;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.stockMinimo = stockMinimo;
        this.forma = forma;
        this.concentracion = concentracion;
        this.requiereReceta = requiereReceta;
        this.activo = activo;
        this.instrucciones = instrucciones;
        this.codigo = codigo;
    }

    // Getters y Setters corregidos
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }

    public String getNombre() { 
        return nombre; 
    }
    
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }

    public String getDescripcion() { 
        return descripcion; 
    }
    
    public void setDescripcion(String descripcion) { 
        this.descripcion = descripcion; 
    }

    public String getFabricante() { 
        return fabricante; 
    }
    
    public void setFabricante(String fabricante) { 
        this.fabricante = fabricante; 
    }

    public String getCategoria() { 
        return categoria; 
    }
    
    public void setCategoria(String categoria) { 
        this.categoria = categoria; 
    }

    public BigDecimal getPrecio() { 
        return precio; 
    }
    
    public void setPrecio(BigDecimal precio) { 
        this.precio = precio; 
    }

    public Integer getStock() { 
        return stock; 
    }
    
    public void setStock(Integer stock) { 
        this.stock = stock; 
    }

    public Integer getStockMinimo() { 
        return stockMinimo; 
    }
    
    public void setStockMinimo(Integer stockMinimo) { 
        this.stockMinimo = stockMinimo; 
    }

    public String getForma() { 
        return forma; 
    }
    
    public void setForma(String forma) { 
        this.forma = forma; 
    }

    public String getConcentracion() { 
        return concentracion; 
    }
    
    public void setConcentracion(String concentracion) { 
        this.concentracion = concentracion; 
    }

    public Boolean getRequiereReceta() { 
        return requiereReceta; 
    }
    
    public void setRequiereReceta(Boolean requiereReceta) { 
        this.requiereReceta = requiereReceta; 
    }

    public Boolean getActivo() { 
        return activo; 
    }
    
    public void setActivo(Boolean activo) { 
        this.activo = activo; 
    }

    public String getInstrucciones() { 
        return instrucciones; 
    }
    
    public void setInstrucciones(String instrucciones) { 
        this.instrucciones = instrucciones; 
    }

    public LocalDateTime getFechaVencimiento() { 
        return fechaVencimiento; 
    }
    
    public void setFechaVencimiento(LocalDateTime fechaVencimiento) { 
        this.fechaVencimiento = fechaVencimiento; 
    }

    public String getCodigo() { 
        return codigo; 
    }
    
    public void setCodigo(String codigo) { 
        this.codigo = codigo; 
    }

    // Métodos de compatibilidad para transición (deprecados)
    @Deprecated
    public String getName() { 
        return this.nombre; 
    }
    
    @Deprecated
    public void setName(String name) { 
        this.nombre = name; 
    }

    @Deprecated
    public String getDescription() { 
        return this.descripcion; 
    }
    
    @Deprecated
    public void setDescription(String description) { 
        this.descripcion = description; 
    }

    @Deprecated
    public String getManufacturer() { 
        return this.fabricante; 
    }
    
    @Deprecated
    public void setManufacturer(String manufacturer) { 
        this.fabricante = manufacturer; 
    }

    @Deprecated
    public String getDosage() { 
        return this.concentracion; 
    }
    
    @Deprecated
    public void setDosage(String dosage) { 
        this.concentracion = dosage; 
    }

    @Deprecated
    public String getForm() { 
        return this.forma; 
    }
    
    @Deprecated
    public void setForm(String form) { 
        this.forma = form; 
    }

    // Métodos compatibles con nombres antiguos (para compatibilidad con DAO existente)
    @Deprecated
    public int getIdMedicamento() { 
        return id != null ? id.intValue() : 0; 
    }
    
    @Deprecated
    public void setIdMedicamento(int idMedicamento) { 
        this.id = Long.valueOf(idMedicamento); 
    }

    @Override
    public String toString() {
        return "Medicamento{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fabricante='" + fabricante + '\'' +
                ", categoria='" + categoria + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                ", forma='" + forma + '\'' +
                ", activo=" + activo +
                '}';
    }
}
