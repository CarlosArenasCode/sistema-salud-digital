package com.clinica.salud.controller;

import com.clinica.salud.modelo.ResultadoLaboratorio;
import com.clinica.salud.service.ResultadoLaboratorioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.util.List;

@Tag(name = "Resultados de Laboratorio", description = "Operaciones relacionadas con resultados de laboratorio")
@RestController
@RequestMapping("/resultados")
@Validated
public class ResultadoLaboratorioController {

    private final ResultadoLaboratorioService resultadoLaboratorioService;

    @Autowired
    public ResultadoLaboratorioController(ResultadoLaboratorioService resultadoLaboratorioService) {
        this.resultadoLaboratorioService = resultadoLaboratorioService;
    }

    @Operation(summary = "Lista todos los resultados de laboratorio")
    @ApiResponse(responseCode = "200", description = "Lista de resultados obtenida correctamente")
    @GetMapping
    public List<ResultadoLaboratorio> listarTodos() {
        return resultadoLaboratorioService.listarTodos();
    }

    @Operation(summary = "Obtiene un resultado de laboratorio por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Resultado encontrado"),
        @ApiResponse(responseCode = "404", description = "Resultado no encontrado")
    })
    @GetMapping("/{id}")
    public ResultadoLaboratorio obtenerPorId(@PathVariable int id) {
        return resultadoLaboratorioService.buscarPorId(id);
    }

    @Operation(summary = "Crea un nuevo resultado de laboratorio")
    @ApiResponse(responseCode = "201", description = "Resultado creado correctamente")
    @PostMapping
    public void crear(@Valid @RequestBody ResultadoLaboratorio resultado) {
        resultadoLaboratorioService.insertar(resultado);
    }

    @Operation(summary = "Actualiza un resultado de laboratorio existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Resultado actualizado"),
        @ApiResponse(responseCode = "404", description = "Resultado no encontrado")
    })
    @PutMapping("/{id}")
    public void actualizar(@PathVariable int id, @Valid @RequestBody ResultadoLaboratorio resultado) {
        resultadoLaboratorioService.actualizar(id, resultado);
    }

    @Operation(summary = "Elimina un resultado de laboratorio por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Resultado eliminado"),
        @ApiResponse(responseCode = "404", description = "Resultado no encontrado")
    })
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable int id) {
        resultadoLaboratorioService.eliminar(id);
    }
}
