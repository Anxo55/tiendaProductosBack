package cebem.tiendaProductos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import cebem.tiendaProductos.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {


    
}
