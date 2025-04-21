package cebem.tiendaProductos.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cebem.tiendaProductos.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    
}
