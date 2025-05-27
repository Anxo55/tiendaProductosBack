package cebem.tiendaProductos.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cebem.tiendaProductos.entities.Cart;
import cebem.tiendaProductos.entities.User;

public interface CartRepository extends JpaRepository<Cart, Long>{
    Optional<Cart> findByUser(User user);    
}
