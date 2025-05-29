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
        // Obtener usuario autenticado
        String usernameAutenticado = userUtils.getUsernameFromContext();
        User usuarioAutenticado = userRepository.findByUsername(usernameAutenticado)
                .orElseThrow(() -> new RuntimeException("Usuario autenticado no encontrado"));

        // Verificar si el usuario autenticado es el mismo que el del ID
        if (!usuarioAutenticado.getId().equals(id)) {
            throw new SecurityException("No tienes permiso para actualizar este perfil.");
        }

        // Obtener datos actuales
        User userExistente = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        // Solo actualizamos campos permitidos
        if (datosActualizados.getUsername() != null) {
            userExistente.setUsername(datosActualizados.getUsername());
        }

        if (datosActualizados.getEmail() != null) {
            userExistente.setEmail(datosActualizados.getEmail());
        }

        // No se permite cambiar roles, password ni imageUrl desde este endpoint

        return userRepository.save(userExistente);
    }

    public User obtenerUsuarioAutenticado() {
        String username = userUtils.getUsernameFromContext();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}
