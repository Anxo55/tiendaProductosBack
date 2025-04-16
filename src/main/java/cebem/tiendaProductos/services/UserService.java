package cebem.tiendaProductos.services;

import java.util.List;

import org.springframework.stereotype.Service;

import cebem.tiendaProductos.entities.User;
import cebem.tiendaProductos.repositories.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;

    public List<User> listarUsuarios() {
        return userRepository.findAll();
    }

    public User listarPorId(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException());
    }

    
    public User actualizarUsuario(Long id, User datosActualizados) {
        User userExistente = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    
        userExistente.setUsername(datosActualizados.getUsername());
        userExistente.setEmail(datosActualizados.getEmail());
    
        return userRepository.save(userExistente);
    }
    
}
