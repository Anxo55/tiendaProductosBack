package cebem.tiendaProductos.services;

import cebem.tiendaProductos.entities.Pedido;
import cebem.tiendaProductos.entities.PedidoItem;
import cebem.tiendaProductos.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarConfirmacionDePedido(User user, Pedido pedido) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(user.getEmail());
        mensaje.setSubject("Confirmación de tu pedido en TiendaProductos");
        mensaje.setText(construirContenido(pedido));

        mailSender.send(mensaje);
        System.out.println("Correo enviado a: " + user.getEmail());
    }

    private String construirContenido(Pedido pedido) {
        StringBuilder sb = new StringBuilder();
        sb.append("Gracias por tu compra.\n");
        sb.append("Fecha: ").append(pedido.getFecha()).append("\n");
        sb.append("Total: ").append(pedido.getTotal()).append(" €\n\n");
        sb.append("Productos:\n");

        for (PedidoItem item : pedido.getItems()) {
            sb.append("- ").append(item.getQuantity()).append(" x ")
              .append(item.getProductName()).append(" - ")
              .append(item.getProductPrice()).append(" €\n");
        }

        return sb.toString();
    }
}
