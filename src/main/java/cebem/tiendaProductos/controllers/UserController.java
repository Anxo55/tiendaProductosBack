package cebem.tiendaProductos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cebem.tiendaProductos.entities.User;
import cebem.tiendaProductos.services.UserService;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin("http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> listarUsuarios() {
        return userService.listarUsuarios();
    }

    @GetMapping("/{id}")
    public User perfilUsuario(@PathVariable Long id) {
        return userService.listarPorId(id);
    }

    @PutMapping("/{id}")
    public User actualizarPerfil(@PathVariable Long id, @RequestBody User userActualizado) {
        return userService.actualizarUsuario(id, userActualizado);
    }

    // Nuevo endpoint para obtener el perfil del usuario autenticado
    @GetMapping("/perfil")
    public User obtenerPerfil() {
        return userService.obtenerUsuarioAutenticado();
    }
}
