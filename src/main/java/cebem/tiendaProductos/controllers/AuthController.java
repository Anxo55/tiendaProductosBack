package cebem.tiendaProductos.controllers;

import cebem.tiendaProductos.config.JwtUtil;
import cebem.tiendaProductos.dto.LoginDto;
import cebem.tiendaProductos.models.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    // Endpoint para hacer login y generar un token JWT
    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginDto request) {
        // Autenticación con el AuthenticationManager
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username,
                        request.password
                )
        );

        // Cargar los detalles del usuario y generar un JWT
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.username);
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        return new AuthenticationResponse(jwt); // Devuelve el JWT
    }
}
