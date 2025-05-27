package cebem.tiendaProductos.controllers;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cebem.tiendaProductos.entities.Product;
import cebem.tiendaProductos.services.ProductService;
import lombok.RequiredArgsConstructor;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/productos")
@CrossOrigin("http://localhost:4200")
@RequiredArgsConstructor
public class ProductController {

    private ProductService productService;

    @GetMapping
    public List<Product> listarProductos() {
        return productService.listarProductos();
    }

    @GetMapping("/{id}")
    public Product listarProductoPorId(Long id) {
        return productService.listarProductoPorId(id);
    } 

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Product crearProducto(Product product) {
        return productService.crearProducto(product);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    public void borrarProducto(Long id) {
        productService.borrarProducto(id);
    }
    
}
