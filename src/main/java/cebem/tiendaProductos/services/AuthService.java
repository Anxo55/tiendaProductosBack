package cebem.tiendaProductos.services;

import java.util.Set;

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

        User user = User.builder()
                .username(dto.username)
                .email(dto.email)
                .password(passwordEncoder.encode(dto.password))
                .roles(Set.of("USER"))
                .build();

        userRepository.save(user);
    }
    
}
