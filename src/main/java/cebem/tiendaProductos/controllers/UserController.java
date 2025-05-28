package cebem.tiendaProductos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import cebem.tiendaProductos.entities.User;
import cebem.tiendaProductos.services.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin("http://localhost:4200")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> listarUsuarios() {
        logger.info("Usuario accediendo a listarUsuarios: " +
            SecurityContextHolder.getContext().getAuthentication().getName());
        logger.info("Roles: " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        return userService.listarUsuarios();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public User perfilUsuario(@PathVariable Long id) {
        logger.info("Usuario accediendo a perfilUsuario: " +
            SecurityContextHolder.getContext().getAuthentication().getName());
        logger.info("Roles: " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        return userService.listarPorId(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public User actualizarPerfil(@PathVariable Long id, @RequestBody User userActualizado) {
        return userService.actualizarUsuario(id, userActualizado);
    }

    @GetMapping("/perfil")
    @PreAuthorize("isAuthenticated()")
    public User obtenerPerfil() {
        return userService.obtenerUsuarioAutenticado();
    }
}
