package cebem.tiendaProductos.controllers;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import cebem.tiendaProductos.dto.ProductDto;
import cebem.tiendaProductos.entities.Product;
import cebem.tiendaProductos.services.ProductService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin("http://localhost:4200")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<Product> listarProductos() {
        return productService.listarProductos();
    }

    @GetMapping("/{id}")
    public Product listarProductoPorId(@PathVariable Long id) {
        return productService.listarProductoPorId(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Product crearProducto(@RequestBody ProductDto productDto) {
        return productService.crearProducto(productDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void borrarProducto(@PathVariable Long id) {
        productService.borrarProducto(id);
    }
}
