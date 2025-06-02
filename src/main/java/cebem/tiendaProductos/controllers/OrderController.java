package cebem.tiendaProductos.controllers;

import cebem.tiendaProductos.entities.Order;
import cebem.tiendaProductos.services.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ordenes")
@CrossOrigin("http://localhost:4200")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/comprar")
    @PreAuthorize("isAuthenticated()")
    public Order realizarCompra() {
        return orderService.realizarCompra();
    }
}
