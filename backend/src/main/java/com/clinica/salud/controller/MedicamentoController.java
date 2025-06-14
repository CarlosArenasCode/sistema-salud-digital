package com.clinica.salud.controller;

import com.clinica.salud.entity.MedicamentoEntity;
import com.clinica.salud.service.BaseService;
import com.clinica.salud.service.MedicamentoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Medicamentos", description = "Operaciones relacionadas con medicamentos")
@RestController
@RequestMapping("/medicamentos")
@Validated
public class MedicamentoController extends BaseController<MedicamentoEntity, Long> {

    private final MedicamentoService medicamentoService;

    @Autowired
    public MedicamentoController(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
    }    @Override
    protected BaseService<MedicamentoEntity, Long> getService() {
        return medicamentoService;
    }

    // Métodos adicionales específicos de medicamentos pueden ir aquí
    // Por ejemplo: buscar por nombre, buscar por categoría, verificar stock bajo, etc.
}
