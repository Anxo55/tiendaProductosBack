package cebem.tiendaProductos.controllers;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import cebem.tiendaProductos.dto.CategoryDto;
import cebem.tiendaProductos.entities.Category;
import cebem.tiendaProductos.services.CategoryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin("http://localhost:4200")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<Category> listarCategorias() {
        return categoryService.listarCategorias();
    }

    @GetMapping("/{id}")
    public Category listarCategoriaPorId(@PathVariable Long id) {
        return categoryService.listarCategoriaPorId(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Category crearCategoria(@RequestBody CategoryDto categoryDto) {
        return categoryService.crearCategoria(categoryDto);
    }
}
