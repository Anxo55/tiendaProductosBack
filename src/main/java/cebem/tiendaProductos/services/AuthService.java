package cebem.tiendaProductos.services;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cebem.tiendaProductos.dto.RegisterDto;
import cebem.tiendaProductos.entities.User;
import cebem.tiendaProductos.repositories.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(RegisterDto dto) {
        if (userRepository.existsByUsername(dto.username)) {
            throw new RuntimeException("El nombre de usuario ya está en uso");
        }

        Set<String> roles = (dto.roles == null || dto.roles.isEmpty()) 
            ? Set.of("USER") 
            : dto.roles.stream()
                .map(String::toUpperCase) // <- pasa los roles a mayúscula
                .collect(Collectors.toSet());

        User user = User.builder()
                .username(dto.username)
                .email(dto.email)
                .password(passwordEncoder.encode(dto.password))
                .imageUrl(dto.imageUrl)
                .roles(roles)
                .build();

        userRepository.save(user);
    }
}
