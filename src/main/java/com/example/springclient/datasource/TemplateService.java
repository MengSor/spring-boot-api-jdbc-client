package com.example.springclient.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;

@Service
public class TemplateService implements ProductService{

    private static final Logger log = LoggerFactory.getLogger(TemplateService.class);
    private final JdbcTemplate jdbcTemplate;
    public TemplateService (JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    RowMapper<Product> rowMapper = (rs,row) -> new Product(
            rs.getString("id"),
            rs.getString("firstname"),
            rs.getString("lastname")
    );

    @Override
    public List<Product> findAll() {
        var sql = "SELECT id,firstname,lastname FROM product";
        return jdbcTemplate.query(sql,rowMapper);
    }

    @Override
    public Optional<Product> findId(String id) {
        var sql = "SELECT id,firstname,lastname FROM product WHERE id = ?";
        Product product = null;
        try {
           product = jdbcTemplate.queryForObject(sql,rowMapper,id);
        }catch (DataAccessException ex){
            log.info("Product not found " + id,ex);
        }
        return Optional.ofNullable(product);
    }

    @Override
    public void create(Product product) {
            String sql = "INSERT INTO product(id,firstname,lastname) Values (?,?,?)";
            int insert = jdbcTemplate.update(sql,product.id(),product.firstname(),product.lastname());
            if (insert == 1){
                log.info("New Creat:"+ product.firstname());
            }
    }

    @Override
    public void update(Product product, String id) {
        var sql = "UPDATE product SET firstname = ? , lastname = ? WHERE id = ?";
        int update = jdbcTemplate.update(sql,product.firstname(),product.lastname(),id);
        if (update == 1){
            log.info("Update Product: " + product.firstname());
        }
    }

    @Override
    public void delete(String id) {
        var slq = "DELETE FROM product WHERE id = ?";
        int delete = jdbcTemplate.update(slq,id);
        if (delete == 1){
            log.info("Delete product: " + id);
        }
    }
}
