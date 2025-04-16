package cebem.tiendaProductos.services;

import java.util.List;

import org.springframework.stereotype.Service;

import cebem.tiendaProductos.entities.Product;
import cebem.tiendaProductos.repositories.ProductRepository;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public List<Product> listarProductos() {
        return productRepository.findAll();
    }

    public Product listarProductoPorId(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException());
    }

    public Product crearProducto( Product product) {
        return productRepository.save(product);
    }

    public void borrarProducto(Long id) {
        productRepository.deleteById(id);
    }
    
}
