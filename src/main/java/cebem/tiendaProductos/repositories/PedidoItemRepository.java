package cebem.tiendaProductos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import cebem.tiendaProductos.entities.PedidoItem;

public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long>{
    
}
