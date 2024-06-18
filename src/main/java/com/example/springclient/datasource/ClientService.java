package com.example.springclient.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService implements ProductService{
    @Autowired
    private JdbcClient jdbcClient;



    @Override
    public List<Product> findAll() {
        return jdbcClient.sql("SELECT id,firstname,lastname FROM product")
                .query(Product.class)
                .list();
    }

    @Override
    public Optional<Product> findId(String id) {
        return jdbcClient.sql("SELECT id,firstname,lastname FROM product WHERE id = :id")
                .param("id", id)
                .query(Product.class)
                .optional();
    }

    @Override
    public void create(Product product) {
        var update = jdbcClient.sql("INSERT INTO product(id,firstname,lastname) VALUES (?,?,?)")
                .params(List.of(product.id(),product.firstname(),product.lastname()))
                .update();

        Assert.state(update == 1,"False create"+ product.firstname());
    }

    @Override
    public void update(Product product, String id) {
        var update = jdbcClient.sql("UPDATE product SET firstname = ? , lastname = ? WHERE id = ?")
                .params(List.of(product.firstname(),product.lastname(),id))
                .update();

        Assert.state(update == 1,"failed Update product" + product.firstname());
    }

    @Override
    public void delete(String id) {
        var delete = jdbcClient.sql("DELETE FROM product WHERE id = :id")
                .param("id", id)
                .update();

        Assert.state(delete == 1,"Failed delete " + id);
    }
}
