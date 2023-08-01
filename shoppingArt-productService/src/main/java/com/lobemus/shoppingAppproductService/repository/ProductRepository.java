package com.lobemus.shoppingAppproductService.repository;

import com.lobemus.shoppingAppproductService.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {

}
