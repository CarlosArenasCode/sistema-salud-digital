package com.clinica.salud.controller;

import com.clinica.salud.modelo.Usuario;
import com.clinica.salud.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Tag(name = "Usuarios", description = "Operaciones relacionadas con usuarios")
@RestController
@RequestMapping("/usuarios")
@Validated
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(summary = "Lista todos los usuarios")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida correctamente")
    @GetMapping
    public List<Usuario> listarTodos() {
        return usuarioService.listarTodos();
    }

    @Operation(summary = "Obtiene un usuario por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public Usuario obtenerPorId(@PathVariable int id) {
        return usuarioService.buscarPorId(id);
    }

    @Operation(summary = "Crea un nuevo usuario")
    @ApiResponse(responseCode = "201", description = "Usuario creado correctamente")
    @PostMapping
    public void crear(@Valid @RequestBody Usuario usuario) {
        usuarioService.insertar(usuario);
    }

    @Operation(summary = "Actualiza un usuario existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario actualizado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PutMapping("/{id}")
    public void actualizar(@PathVariable int id, @Valid @RequestBody Usuario usuario) {
        usuarioService.actualizar(id, usuario);
    }

    @Operation(summary = "Elimina un usuario por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Usuario eliminado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable int id) {
        usuarioService.eliminar(id);
    }
}
