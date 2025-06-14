package com.clinica.salud.controller;

import com.clinica.salud.entity.CitaEntity;
import com.clinica.salud.service.BaseService;
import com.clinica.salud.service.CitaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Citas", description = "Operaciones relacionadas con citas")
@RestController
@RequestMapping("/citas")
@Validated
public class CitaController extends BaseController<CitaEntity, Long> {
    
    private final CitaService citaService;

    @Autowired
    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }    @Override
    protected BaseService<CitaEntity, Long> getService() {
        return citaService;
    }

    // Métodos adicionales específicos de citas pueden ir aquí
    // Por ejemplo: buscarPorFecha, buscarPorPaciente, buscarPorMedico, etc.
}