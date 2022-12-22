package com.example.demo.src.upload;

import com.example.demo.src.product.model.PostProductReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UploadDao {
    private JdbcTemplate jdbcTemplate;


    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int createImg(PostProductReq postProductReq){
        String createImgQuery = "insert into Product (images) VALUES (?)";
        Object[] createImgParams = new Object[]{ postProductReq.getProductName(),
               postProductReq.getImages()};
        this.jdbcTemplate.update(createImgQuery, createImgParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

}
