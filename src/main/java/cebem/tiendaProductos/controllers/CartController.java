package cebem.tiendaProductos.controllers;

import cebem.tiendaProductos.dto.CartDto;
import cebem.tiendaProductos.entities.Pedido;
import cebem.tiendaProductos.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carrito")
@CrossOrigin("http://localhost:4200")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public CartDto obtenerCarrito() {
        return cartService.getCartDtoForCurrentUser();
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public CartDto agregarProducto(@RequestParam Long productId, @RequestParam int quantity) {
        return cartService.addProductToCart(productId, quantity);
    }

    @DeleteMapping("/remove")
    @PreAuthorize("isAuthenticated()")
    public CartDto eliminarProducto(@RequestParam Long productId) {
        return cartService.removeProductFromCart(productId);
    }

    @DeleteMapping("/clear")
    @PreAuthorize("isAuthenticated()")
    public CartDto vaciarCarrito() {
        return cartService.clearCart();
    }

    @PostMapping("/comprar")
    @PreAuthorize("isAuthenticated()")
    public Pedido realizarCompra() {
        return cartService.realizarCompra();
    }

}
