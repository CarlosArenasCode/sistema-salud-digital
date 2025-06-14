package com.clinica.salud.controller;

import com.clinica.salud.entity.CitaEntity;
import com.clinica.salud.service.BaseService;
import com.clinica.salud.service.CitaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/citas")
@Tag(name = "Citas")
@Validated
public class CitaController extends BaseController<CitaEntity, Long> {
    
    private final CitaService service;

    public CitaController(CitaService service) {
        this.service = service;
    }

    @Override
    protected BaseService<CitaEntity, Long> getService() { return service; }
}