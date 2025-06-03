package cebem.tiendaProductos.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cebem.tiendaProductos.dto.CartDto;
import cebem.tiendaProductos.dto.CartItemDto;
import cebem.tiendaProductos.entities.Cart;
import cebem.tiendaProductos.entities.CartItem;
import cebem.tiendaProductos.entities.Pedido;
import cebem.tiendaProductos.entities.PedidoItem;
import cebem.tiendaProductos.entities.Product;
import cebem.tiendaProductos.entities.User;
import cebem.tiendaProductos.repositories.CartItemRepository;
import cebem.tiendaProductos.repositories.CartRepository;
import cebem.tiendaProductos.repositories.PedidoRepository;
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

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private EmailService emailService;

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

    public CartDto addProductToCart(Long productId, int quantity) {
        if (quantity <= 0) {
            throw new RuntimeException("La cantidad debe ser mayor que cero");
        }

        Cart cart = getOrCreateCartForCurrentUser();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

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

        return convertToDTO(cart);
    }

    public CartDto removeProductFromCart(Long productId) {
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

        return convertToDTO(cart);
    }

    public CartDto clearCart() {
        Cart cart = getOrCreateCartForCurrentUser();

        cart.getItems().clear();
        cartItemRepository.deleteAll(cart.getItems());
        cartRepository.save(cart);

        return convertToDTO(cart);
    }

    public CartDto getCartDtoForCurrentUser() {
        Cart cart = getOrCreateCartForCurrentUser();
        return convertToDTO(cart);
    }

    public CartDto convertToDTO(Cart cart) {
        List<CartItemDto> items = cart.getItems().stream().map(item -> CartItemDto.builder()
                .productId(item.getProduct().getId())
                .productName(item.getProduct().getName())
                .productPrice(item.getProduct().getPrice())
                .productImageUrl(item.getProduct().getImageUrl())
                .quantity(item.getQuantity())
                .build()).collect(Collectors.toList());

        return CartDto.builder().items(items).build();
    }

    public Pedido realizarCompra() {
    Cart cart = getOrCreateCartForCurrentUser();
    User user = cart.getUser();

    if (cart.getItems().isEmpty()) {
        throw new RuntimeException("El carrito está vacío");
    }

    Pedido pedido = Pedido.builder()
            .fecha(LocalDateTime.now())
            .user(user)
            .build();

    double total = 0;
    List<PedidoItem> items = new ArrayList<>();

    for (CartItem cartItem : cart.getItems()) {
        PedidoItem item = PedidoItem.builder()
                .productName(cartItem.getProduct().getName())
                .productPrice(cartItem.getProduct().getPrice())
                .quantity(cartItem.getQuantity())
                .pedido(pedido)
                .build();
        total += item.getProductPrice() * item.getQuantity();
        items.add(item);
    }

    pedido.setItems(items);
    pedido.setTotal(total);
    pedidoRepository.save(pedido);

    cart.getItems().clear();
    cartRepository.save(cart);

    emailService.enviarConfirmacionDePedido(user, pedido);

    return pedido;
}

}
