package com.app.ecom_application.service;


import com.app.ecom_application.dto.ProductRequest;
import com.app.ecom_application.dto.ProductResponse;
import com.app.ecom_application.model.Product;
import com.app.ecom_application.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    public ProductResponse createProduct(ProductRequest productRequest) {

        Product product = new Product();
        updateProductFromRequest(product,productRequest);
        Product savedProduct = productRepository.save(product);
        return  mapToProductResponse (savedProduct);

    }

    private ProductResponse mapToProductResponse(Product savedProduct) {
       ProductResponse response = new ProductResponse();
        response.setId(savedProduct.getId());
        response.setName(savedProduct.getName());
        response.setActive(savedProduct.getActive());
        response.setCategory(savedProduct.getCategory());
        response.setDescription(savedProduct.getDescription());
        response.setPrice(savedProduct.getPrice());
        response.setImageUrl(savedProduct.getImageUrl());
        response.setStockQuantity(savedProduct.getStockQuantity());

        return response;


    }

    private void updateProductFromRequest(Product product, ProductRequest productRequest) {

        product.setName(productRequest.getName());
        product.setCategory(productRequest.getCategory());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setImageUrl(productRequest.getImageUrl());
        product.setStockQuantity(productRequest.getStockQuantity());


    }
    // get the product from the Db and update it
    public Optional<ProductResponse> updateProduct(Long id, ProductRequest productRequest) {

        return productRepository.findById(id)
                 .map(exitingProduct -> {
                     updateProductFromRequest(exitingProduct,productRequest);
                 Product savedProduct = productRepository.save(exitingProduct);
                    return mapToProductResponse(savedProduct);
                 });


    }

    public List<ProductResponse> getAllProduct() {

           return productRepository.findByActiveTrue().stream()
                   .map(this :: mapToProductResponse)
                   .collect(Collectors.toList());



    }

    public void deleteProduct(Long id) {

          Product product = productRepository.findById(id)
                  .orElseThrow(()->new RuntimeException("Product not found"));
           product.setActive(false);
         productRepository.save(product);
    }

    public List<ProductResponse> searchProducts(String keyword) {
         return productRepository.searchProducts(keyword).stream()
                 .map(this:: mapToProductResponse)
                 .collect(Collectors.toList());

    }
}
