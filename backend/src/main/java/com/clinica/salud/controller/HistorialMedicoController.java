package com.clinica.salud.controller;

import com.clinica.salud.entity.HistorialMedicoEntity;
import com.clinica.salud.service.HistorialMedicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.util.List;

@Tag(name = "Historial Médico", description = "Operaciones relacionadas con historiales médicos")
@RestController
@RequestMapping("/historiales")
@Validated
public class HistorialMedicoController {

    private final HistorialMedicoService historialMedicoService;

    @Autowired
    public HistorialMedicoController(HistorialMedicoService historialMedicoService) {
        this.historialMedicoService = historialMedicoService;
    }    @Operation(summary = "Lista todos los historiales médicos")
    @ApiResponse(responseCode = "200", description = "Lista de historiales obtenida correctamente")
    @GetMapping
    public List<HistorialMedicoEntity> listarTodos() {
        return historialMedicoService.listarTodos();
    }

    @Operation(summary = "Obtiene un historial médico por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Historial encontrado"),
        @ApiResponse(responseCode = "404", description = "Historial no encontrado")
    })
    @GetMapping("/{id}")
    public HistorialMedicoEntity obtenerPorId(@PathVariable Long id) {
        return historialMedicoService.buscarPorId(id);
    }

    @Operation(summary = "Crea un nuevo historial médico")
    @ApiResponse(responseCode = "201", description = "Historial creado correctamente")
    @PostMapping
    public HistorialMedicoEntity crear(@Valid @RequestBody HistorialMedicoEntity historial) {
        return historialMedicoService.insertar(historial);
    }

    @Operation(summary = "Actualiza un historial médico existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Historial actualizado"),
        @ApiResponse(responseCode = "404", description = "Historial no encontrado")
    })
    @PutMapping("/{id}")
    public HistorialMedicoEntity actualizar(@PathVariable Long id, @Valid @RequestBody HistorialMedicoEntity historial) {
        return historialMedicoService.actualizar(id, historial);
    }

    @Operation(summary = "Elimina un historial médico por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Historial eliminado"),
        @ApiResponse(responseCode = "404", description = "Historial no encontrado")
    })
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        historialMedicoService.eliminar(id);
    }
}