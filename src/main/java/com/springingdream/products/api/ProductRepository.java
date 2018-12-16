package com.springingdream.products.api;

import com.springingdream.products.api.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {
}
