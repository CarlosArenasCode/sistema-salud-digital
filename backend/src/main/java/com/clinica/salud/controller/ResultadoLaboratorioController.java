package com.clinica.salud.controller;

import com.clinica.salud.entity.ResultadoLaboratorioEntity;
import com.clinica.salud.service.BaseService;
import com.clinica.salud.service.ResultadoLaboratorioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

@Tag(name = "Resultados de Laboratorio", description = "Operaciones relacionadas con resultados de laboratorio")
@RestController
@RequestMapping("/resultados")
@Validated
public class ResultadoLaboratorioController extends BaseController<ResultadoLaboratorioEntity, Long> {

    private final ResultadoLaboratorioService resultadoLaboratorioService;

    @Autowired
    public ResultadoLaboratorioController(ResultadoLaboratorioService resultadoLaboratorioService) {
        this.resultadoLaboratorioService = resultadoLaboratorioService;
    }    @Override
    protected BaseService<ResultadoLaboratorioEntity, Long> getService() {
        return resultadoLaboratorioService;
    }

    // Métodos adicionales específicos de resultados de laboratorio pueden ir aquí
    // Los métodos CRUD básicos (GET, POST, PUT, DELETE) ya están implementados en BaseController
}
