package cebem.tiendaProductos.controllers;

import cebem.tiendaProductos.config.JwtUtil;
import cebem.tiendaProductos.dto.LoginDto;
import cebem.tiendaProductos.dto.RegisterDto;
import cebem.tiendaProductos.entities.AuthenticationResponse;
import cebem.tiendaProductos.security.CustomUserDetailsService;
import cebem.tiendaProductos.services.AuthService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("http://localhost:4200")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto request) {
        // Autenticación con el AuthenticationManager
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username, request.password));

        // Generación del token JWT utilizando el Authentication generado
        String token = jwtUtil.generateJwtToken(authentication);
        System.out.println("JWT generado: " + token); // Agregar un log aquí
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDto dto) {
        authService.register(dto);
        return ResponseEntity.status(201).body("Usuario registrado exitosamente.");

    }
}

