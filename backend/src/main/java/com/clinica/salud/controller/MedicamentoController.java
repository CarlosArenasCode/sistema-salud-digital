package com.clinica.salud.controller;

import com.clinica.salud.entity.MedicamentoEntity;
import com.clinica.salud.service.BaseService;
import com.clinica.salud.service.MedicamentoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicamentos")
@Tag(name = "Medicamentos")
@Validated
public class MedicamentoController extends BaseController<MedicamentoEntity, Long> {

    private final MedicamentoService service;

    public MedicamentoController(MedicamentoService service) {
        this.service = service;
    }

    @Override
    protected BaseService<MedicamentoEntity, Long> getService() { return service; }
}
