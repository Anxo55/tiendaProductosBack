package cebem.tiendaProductos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cebem.tiendaProductos.entities.Pedido;
import cebem.tiendaProductos.entities.User;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{

    List<Pedido> findByUser(User user);
    
}
