package com.app.ecom_application.controller;


import com.app.ecom_application.dto.CartItemRequest;
import com.app.ecom_application.model.CartItem;
import com.app.ecom_application.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {


    private final CartService cartService;


    // Adding item to cart
    @PostMapping
    public ResponseEntity<String> addToCart(
            // two things (user - cart)
              @RequestHeader ("X-User-ID") String userId,
              @RequestBody CartItemRequest request){

          if (!cartService.addToCard(userId, request)){
              return ResponseEntity.badRequest().body("Product out of stock or user not found or product not found");


          }
          return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    // remove item from card
    @DeleteMapping ("/items/{productId}")
    public ResponseEntity<Void> removeFromCart(
            @RequestHeader("X-User-ID") String userId,
            @PathVariable Long productId){
     boolean deleted =  cartService.deleteItemFromCart(userId , productId);
         return deleted ? ResponseEntity.noContent().build()
                 : ResponseEntity.notFound().build();
    }

    // fetching the cart
    @GetMapping
    public ResponseEntity<List<CartItem>> getCart(
      @RequestHeader("X-User-ID") String userId){
        return ResponseEntity.ok(cartService.getCart(userId));

    }

}
