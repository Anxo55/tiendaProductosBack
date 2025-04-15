package cebem.tiendaProductos.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cebem.tiendaProductos.entities.Category;


public interface CategoryRepository extends JpaRepository<Category, Long>{
    Optional<Category> findByName(String name);    
}
