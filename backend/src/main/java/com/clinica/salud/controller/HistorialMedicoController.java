package com.clinica.salud.controller;

import com.clinica.salud.entity.HistorialMedicoEntity;
import com.clinica.salud.service.BaseService;
import com.clinica.salud.service.HistorialMedicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Tag(name = "Historial Médico", description = "Operaciones relacionadas con historiales médicos")
@RestController
@RequestMapping("/historiales")
@Validated
public class HistorialMedicoController extends BaseController<HistorialMedicoEntity, Long> {

    private final HistorialMedicoService historialMedicoService;

    @Autowired
    public HistorialMedicoController(HistorialMedicoService historialMedicoService) {
        this.historialMedicoService = historialMedicoService;
    }    @Override
    protected BaseService<HistorialMedicoEntity, Long> getService() {
        return historialMedicoService;
    }

    // Método específico adicional
    @Operation(summary = "Busca historiales médicos por ID de paciente")
    @ApiResponse(responseCode = "200", description = "Lista de historiales obtenida correctamente")
    @GetMapping("/paciente/{idPaciente}")
    public List<HistorialMedicoEntity> buscarPorPaciente(@PathVariable Long idPaciente) {
        return historialMedicoService.buscarPorPaciente(idPaciente);
    }

    // Los métodos CRUD básicos (GET, POST, PUT, DELETE) ya están implementados en BaseController
}