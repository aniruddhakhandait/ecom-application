package com.app.ecom_application.dto;

import lombok.Data;

@Data
public class CartItemRequest {

    // product and quantity of product
    private Long productId;
    private Integer quantity;


}
