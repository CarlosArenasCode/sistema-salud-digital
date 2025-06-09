package com.clinica.salud.controller;

import com.clinica.salud.entity.MedicoEntity;
import com.clinica.salud.service.BaseService;
import com.clinica.salud.service.MedicoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Médicos", description = "Operaciones relacionadas con médicos")
@RestController
@RequestMapping("/medicos")
@Validated
public class MedicoController extends BaseController<MedicoEntity, Long> {
    
    private final MedicoService medicoService;

    @Autowired
    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @Override
    protected BaseService<MedicoEntity, Long> getService() {
        return medicoService;
    }

    // Métodos adicionales específicos de médicos pueden ir aquí
    // Por ejemplo: buscar por especialidad, listar disponibilidad, etc.
}
