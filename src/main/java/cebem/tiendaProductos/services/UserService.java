package cebem.tiendaProductos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cebem.tiendaProductos.entities.User;
import cebem.tiendaProductos.repositories.UserRepository;
import cebem.tiendaProductos.security.UserUtils;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserUtils userUtils;

    public List<User> listarUsuarios() {
        return userRepository.findAll();
    }

    public User listarPorId(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }

    public User actualizarUsuario(Long id, User datosActualizados) {
        User userExistente = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        userExistente.setUsername(datosActualizados.getUsername());
        userExistente.setEmail(datosActualizados.getEmail());

        return userRepository.save(userExistente);
    }

    // Nuevo método: obtener usuario autenticado desde el contexto de seguridad
    public User obtenerUsuarioAutenticado() {
        String username = userUtils.getUsernameFromContext();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}
