package com.example.demo.src.product;

import com.example.demo.src.product.model.PostProductReq;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class ProductDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int createPost(PostProductReq postProductReq){
        String createPostQuery = "insert into Product (productName, userIdx, categoryIdx, productAddress, description, price) VALUES (?,?,?,?,?,?)";
        Object[] createPostParams = new Object[]{ postProductReq.getProductName(),
                postProductReq.getUserIdx(), postProductReq.getCategoryIdx(), postProductReq.getProductAddress(),
                postProductReq.getDescription(), postProductReq.getPrice()};
        this.jdbcTemplate.update(createPostQuery, createPostParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }


}
