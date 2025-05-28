package cebem.tiendaProductos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cebem.tiendaProductos.dto.ProductDto;
import cebem.tiendaProductos.entities.Category;
import cebem.tiendaProductos.entities.Product;
import cebem.tiendaProductos.repositories.CategoryRepository;
import cebem.tiendaProductos.repositories.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> listarProductos() {
        return productRepository.findAll();
    }

    public Product listarProductoPorId(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public List<Product> listarProductoPorCategoria(Long categoriaId) {
    Category category = categoryRepository.findById(categoriaId)
        .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    return productRepository.findByCategory(category);
}


    public Product crearProducto(ProductDto dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Product product = Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .imageUrl(dto.getImageUrl())
                .category(category)
                .build();

        return productRepository.save(product);
    }

    public void borrarProducto(Long id) {
        productRepository.deleteById(id);
    }
}
