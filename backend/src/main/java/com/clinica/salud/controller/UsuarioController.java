package com.clinica.salud.controller;

import com.clinica.salud.entity.UsuarioEntity;
import com.clinica.salud.service.BaseService;
import com.clinica.salud.service.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Usuarios", description = "Operaciones relacionadas con usuarios")
@RestController
@RequestMapping("/usuarios")
@Validated
public class UsuarioController extends BaseController<UsuarioEntity, Long> {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    protected BaseService<UsuarioEntity, Long> getService() {
        return usuarioService;
    }

    // Métodos adicionales específicos de usuarios pueden ir aquí
    // Los métodos CRUD básicos (GET, POST, PUT, DELETE) ya están implementados en BaseController
}
