package com.app.ecom_application.controller;


import com.app.ecom_application.dto.ProductRequest;
import com.app.ecom_application.dto.ProductResponse;
import com.app.ecom_application.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

      private final ProductService productService;

  // creating the product
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest){
            return new ResponseEntity<ProductResponse>(productService.createProduct(productRequest),

                    HttpStatus.CREATED);

    }

   @GetMapping
   public ResponseEntity<List<ProductResponse>> getProduct(){
        return ResponseEntity.ok(productService.getAllProduct());
   }


    // updating the current product
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequest productRequest){
        return productService.updateProduct(id, productRequest)
                .map(ResponseEntity :: ok)
                .orElseGet(() -> ResponseEntity.notFound().build()) ;

    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> deleteProduct (@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();


    }

    @GetMapping ("/search")
    public ResponseEntity<List<ProductResponse>> searchProduct (@RequestParam String keyword){
      return ResponseEntity.ok(productService.searchProducts(keyword));


    }


}
