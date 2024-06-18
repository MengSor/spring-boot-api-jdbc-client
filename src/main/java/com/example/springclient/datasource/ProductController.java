package com.example.springclient.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    public ProductController(@Qualifier("templateService") ProductService productService) {
        this.productService = productService;
    }
    @GetMapping("")
    public List<Product> findAll(){
        return productService.findAll();
    }
    @GetMapping("/{id}")
    public Optional<Product> findId(@PathVariable String id){
        return productService.findId(id);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Product product){
        productService.create(product);
    }
    @PutMapping("/{id}")
    public void update(@RequestBody Product product, @PathVariable String id){
        productService.update(product,id);  
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        productService.delete(id);
    }

}
