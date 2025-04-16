package cebem.tiendaProductos.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cebem.tiendaProductos.entities.User;
import cebem.tiendaProductos.services.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin("http://localhost:4200")
@RequiredArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping
    public List<User> listarUsuarios() {
        return userService.listarUsuarios();
    }

    @GetMapping("/{id}")
    public User perfilUsuario(Long id) {
        return userService.listarPorId(id);
    }

    @PutMapping("/{id}")
    public User actualizarPerfil(Long id, User userACtualizado) {
        return userService.actualizarUsuario(id, userACtualizado);
    }
    
}
