package cebem.tiendaProductos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cebem.tiendaProductos.entities.Category;
import cebem.tiendaProductos.repositories.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> listarCategorias() {
        return categoryRepository.findAll();
    }

    public Category crearCategoria(Category category) {
        return categoryRepository.save(category);
    }

    public Category listarCategoriaPorId(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException());
    }
    
}
