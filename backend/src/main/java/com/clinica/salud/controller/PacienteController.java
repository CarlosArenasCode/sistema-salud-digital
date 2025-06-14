package com.clinica.salud.controller;

import com.clinica.salud.entity.PacienteEntity;
import com.clinica.salud.service.BaseService;
import com.clinica.salud.service.PacienteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pacientes")
@Tag(name = "Pacientes")
@Validated
public class PacienteController extends BaseController<PacienteEntity, Long> {
    
    private final PacienteService service;
    
    public PacienteController(PacienteService service) {
        this.service = service;
    }

    @Override
    protected BaseService<PacienteEntity, Long> getService() { return service; }
}
