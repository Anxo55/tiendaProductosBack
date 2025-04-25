package cebem.tiendaProductos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cebem.tiendaProductos.entities.Category;
import cebem.tiendaProductos.services.CategoryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/categorias")
@CrossOrigin("http://localhost:4200")
@RequiredArgsConstructor
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    
    @GetMapping
    public List<Category> listarCategorias() {
        return categoryService.listarCategorias();
    }

    @PostMapping
    public Category crearCategoria(Category category) {
        return categoryService.crearCategoria(category);
    }
    
}
