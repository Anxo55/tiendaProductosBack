package cebem.tiendaProductos.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cebem.tiendaProductos.entities.Cart;
import cebem.tiendaProductos.entities.CartItem;
import cebem.tiendaProductos.entities.Product;
import cebem.tiendaProductos.entities.User;
import cebem.tiendaProductos.repositories.CartItemRepository;
import cebem.tiendaProductos.repositories.CartRepository;
import cebem.tiendaProductos.repositories.ProductRepository;
import cebem.tiendaProductos.repositories.UserRepository;
import cebem.tiendaProductos.security.UserUtils;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserUtils userUtils;

    /**
     * Obtiene o crea el carrito asociado al usuario autenticado.
     */
    public Cart getOrCreateCartForCurrentUser() {
        String username = userUtils.getUsernameFromContext();
        if (username == null) {
            throw new RuntimeException("Usuario no autenticado");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return cartRepository.findByUser(user).orElseGet(() -> {
            Cart newCart = Cart.builder().user(user).build();
            return cartRepository.save(newCart);
        });
    }

    /**
     * Añade un producto con una cantidad al carrito del usuario actual.
     */
    public Cart addProductToCart(Long productId, int quantity) {
        if (quantity <= 0) {
            throw new RuntimeException("La cantidad debe ser mayor que cero");
        }

        Cart cart = getOrCreateCartForCurrentUser();

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Buscar si el producto ya está en el carrito
        Optional<CartItem> existingItemOpt = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItemOpt.isPresent()) {
            CartItem existingItem = existingItemOpt.get();
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            cartItemRepository.save(existingItem);
        } else {
            CartItem newItem = CartItem.builder()
                    .product(product)
                    .quantity(quantity)
                    .cart(cart)
                    .build();
            cart.getItems().add(newItem);
            cartItemRepository.save(newItem);
        }

        return cartRepository.save(cart);
    }

    /**
     * Elimina un producto del carrito del usuario actual.
     */
    public Cart removeProductFromCart(Long productId) {
        Cart cart = getOrCreateCartForCurrentUser();

        Optional<CartItem> existingItemOpt = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItemOpt.isPresent()) {
            CartItem item = existingItemOpt.get();
            cart.getItems().remove(item);
            cartItemRepository.delete(item);
            cartRepository.save(cart);
        } else {
            throw new RuntimeException("El producto no está en el carrito");
        }

        return cart;
    }

    /**
     * Vacía el carrito del usuario actual.
     */
    public void clearCart() {
        Cart cart = getOrCreateCartForCurrentUser();

        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
