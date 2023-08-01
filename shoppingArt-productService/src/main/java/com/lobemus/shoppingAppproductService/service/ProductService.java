package com.lobemus.shoppingAppproductService.service;

import com.lobemus.shoppingAppproductService.dto.ProductRequest;
import com.lobemus.shoppingAppproductService.dto.ProductResponse;
import com.lobemus.shoppingAppproductService.model.Product;
import com.lobemus.shoppingAppproductService.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    //Servis direkt olarak db ile bağlantılı
    private final ProductRepository productRepository;
    //if we didn't use RequiredArgsConstructor,
    // then we would need to initialize it manually
    /*
     public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
     }
     */
    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);

        // {} is placeholder coming from Slf4j
        log.info("Product {} is saved!", product.getId());
    }

    public List<ProductResponse> getAllProducts()
    {
        List<Product> products = productRepository.findAll();

        //lambda expression product -> mapToProductResponse(product)
        //replaced with this::mapToProductResponse
       return products.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        ProductResponse productResponse = ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
        return productResponse;
    }
}
