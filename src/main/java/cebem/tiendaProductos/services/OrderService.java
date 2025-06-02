package cebem.tiendaProductos.services;

import cebem.tiendaProductos.entities.*;
import cebem.tiendaProductos.repositories.OrderRepository;
import cebem.tiendaProductos.repositories.UserRepository;
import cebem.tiendaProductos.security.UserUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserUtils userUtils;

    /**
     * Realiza la compra de los productos en el carrito actual.
     */
    public Order realizarCompra() {
        Cart cart = cartService.getOrCreateCartForCurrentUser();

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("El carrito está vacío");
        }

        User user = cart.getUser();

        Order nuevaOrden = Order.builder()
                .user(user)
                .orderDate(LocalDateTime.now())
                .build();

        for (CartItem item : cart.getItems()) {
            OrderItem orderItem = OrderItem.builder()
                    .product(item.getProduct())
                    .quantity(item.getQuantity())
                    .order(nuevaOrden)
                    .build();
            nuevaOrden.getItems().add(orderItem);
        }

        // Guardar la orden y vaciar el carrito
        Order savedOrder = orderRepository.save(nuevaOrden);
        cartService.clearCart();

        return savedOrder;
    }
}
