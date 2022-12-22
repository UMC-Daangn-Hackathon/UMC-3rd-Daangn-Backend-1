package com.example.demo.src.product;

import com.example.demo.src.product.model.GetProductRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ProductDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetProductRes> getProducts(String productAddress){
        String getProductsQuery = "select * from Product inner join ProductImage where productAddress = ? and (status = 'Active' or status = 'Reserved')";
        return this.jdbcTemplate.query(getProductsQuery,
                (rs,rowNum) -> new GetProductRes(
                        rs.getString("productName"),
                        rs.getString("productAddress"),
                        rs.getInt("price"),
                        rs.getString("createdAt"),
                        rs.getString("updatedAt"),
                        rs.getString("image"))
                ,
                        productAddress);
    }
}
