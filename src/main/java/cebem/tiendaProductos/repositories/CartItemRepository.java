package cebem.tiendaProductos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import cebem.tiendaProductos.entities.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{
    
}
