package cebem.tiendaProductos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import cebem.tiendaProductos.entities.Cart;
import cebem.tiendaProductos.services.CartService;

@RestController
@RequestMapping("/api/carrito")
@CrossOrigin("http://localhost:4200")
public class CartController {

    @Autowired
    private CartService cartService;

    // Obtener carrito actual del usuario logueado
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public Cart obtenerCarrito() {
        return cartService.getOrCreateCartForCurrentUser();
    }

    // Añadir producto al carrito
    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public Cart agregarProducto(@RequestParam Long productId, @RequestParam int quantity) {
        return cartService.addProductToCart(productId, quantity);
    }

    // Eliminar producto del carrito
    @DeleteMapping("/remove")
    @PreAuthorize("isAuthenticated()")
    public Cart eliminarProducto(@RequestParam Long productId) {
        return cartService.removeProductFromCart(productId);
    }

    // Vaciar carrito
    @DeleteMapping("/clear")
    @PreAuthorize("isAuthenticated()")
    public void vaciarCarrito() {
        cartService.clearCart();
    }
}
