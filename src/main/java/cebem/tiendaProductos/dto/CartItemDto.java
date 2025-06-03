package cebem.tiendaProductos.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CartItemDto {

    private Long productId;
    private String productName;
    private String productImageUrl;
    private double productPrice;
    private int quantity;
    
}
