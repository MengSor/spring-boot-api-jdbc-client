package com.example.springclient.datasource;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAll();
    Optional<Product> findId(String id);
    void create(Product product);
    void update(Product product,String id);
    void delete(String id);

}
