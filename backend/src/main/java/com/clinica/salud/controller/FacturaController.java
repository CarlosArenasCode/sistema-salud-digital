package com.clinica.salud.controller;

import com.clinica.salud.modelo.Factura;
import com.clinica.salud.service.FacturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.util.List;

@Tag(name = "Facturas", description = "Operaciones relacionadas con facturas")
@RestController
@RequestMapping("/facturas")
@Validated
public class FacturaController {

    private final FacturaService facturaService;

    @Autowired
    public FacturaController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @Operation(summary = "Lista todas las facturas")
    @ApiResponse(responseCode = "200", description = "Lista de facturas obtenida correctamente")
    @GetMapping
    public List<Factura> listarTodos() {
        return facturaService.listarTodos();
    }

    @Operation(summary = "Obtiene una factura por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Factura encontrada"),
        @ApiResponse(responseCode = "404", description = "Factura no encontrada")
    })
    @GetMapping("/{id}")
    public Factura obtenerPorId(@PathVariable int id) {
        return facturaService.buscarPorId(id);
    }

    @Operation(summary = "Crea una nueva factura")
    @ApiResponse(responseCode = "201", description = "Factura creada correctamente")
    @PostMapping
    public void crear(@Valid @RequestBody Factura factura) {
        facturaService.insertar(factura);
    }

    @Operation(summary = "Actualiza una factura existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Factura actualizada"),
        @ApiResponse(responseCode = "404", description = "Factura no encontrada")
    })
    @PutMapping("/{id}")
    public void actualizar(@PathVariable int id, @Valid @RequestBody Factura factura) {
        facturaService.actualizar(id, factura);
    }

    @Operation(summary = "Elimina una factura por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Factura eliminada"),
        @ApiResponse(responseCode = "404", description = "Factura no encontrada")
    })
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable int id) {
        facturaService.eliminar(id);
    }
}
