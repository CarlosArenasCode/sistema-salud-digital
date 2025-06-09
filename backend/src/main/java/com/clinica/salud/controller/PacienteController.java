package com.clinica.salud.controller;

import com.clinica.salud.entity.PacienteEntity;
import com.clinica.salud.service.BaseService;
import com.clinica.salud.service.PacienteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Pacientes", description = "Operaciones relacionadas con pacientes")
@RestController
@RequestMapping("/pacientes")
@Validated
public class PacienteController extends BaseController<PacienteEntity, Long> {
    
    private final PacienteService pacienteService;

    @Autowired
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @Override
    protected BaseService<PacienteEntity, Long> getService() {
        return pacienteService;
    }    // Solo métodos específicos que no están en BaseController van aquí
    // Los métodos CRUD básicos (GET, POST, PUT, DELETE) ya están implementados en BaseController
}
