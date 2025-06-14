package com.clinica.salud.controller;

import com.clinica.salud.entity.MedicoEntity;
import com.clinica.salud.service.BaseService;
import com.clinica.salud.service.MedicoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicos")
@Tag(name = "Médicos")
@Validated
public class MedicoController extends BaseController<MedicoEntity, Long> {
    
    private final MedicoService service;
    
    public MedicoController(MedicoService service) {
        this.service = service;
    }

    @Override
    protected BaseService<MedicoEntity, Long> getService() { return service; }
}
