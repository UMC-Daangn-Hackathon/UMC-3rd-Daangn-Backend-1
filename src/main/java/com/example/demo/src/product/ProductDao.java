package com.example.demo.src.product;

import com.example.demo.src.product.model.*;
import com.example.demo.utils.JwtService;
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
        String getProductsQuery =
                "select * from Product inner join ProductImage on Product.productIdx = ProductImage.productIdx where imageIdx in (select imageIdx from ProductImage where productIdx in (select productIdx from Product where productAddress = ? and (status = 'Active' or status = 'Reserved')) GROUP BY productIdx) group by Product.productIdx";
        return this.jdbcTemplate.query(getProductsQuery,
                (rs,rowNum) -> new GetProductRes(
                        rs.getInt("Product.productIdx"),
                        rs.getString("productName"),
                        rs.getString("productAddress"),
                        rs.getInt("price"),
                        rs.getString("createdAt"),
                        rs.getString("updatedAt"),
                        rs.getString("image"))
                ,
                productAddress);
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

    public List<GetProductRes> getProductsByCategory(String productAddress, String category) {
        String getProductsQuery =
                "select * from Product inner join ProductImage on Product.productIdx = ProductImage.productIdx where imageIdx in (select imageIdx from ProductImage where productIdx in (select productIdx from Product where productAddress = ? and (status = 'Active' or status = 'Reserved') and categoryIdx = (select Category.categoryIdx from Category where categoryName = ?)) GROUP BY productIdx) group by Product.productIdx";
//        String getProductsQuery = "select * from Product inner join ProductImage where productAddress = ? and (status = 'Active' or status = 'Reserved') and categoryIdx = (select Category.categoryIdx from Category where categoryName = ?)";
        return this.jdbcTemplate.query(getProductsQuery,
                (rs,rowNum) -> new GetProductRes(
                        rs.getInt("productIdx"),
                        rs.getString("productName"),
                        rs.getString("productAddress"),
                        rs.getInt("price"),
                        rs.getString("createdAt"),
                        rs.getString("updatedAt"),
                        rs.getString("image"))
                ,
                productAddress, category);
    }

    public List<GetProductRes> getProductsByKeyword(String productAddress, String keyword) {
        String getProductsQuery =
                "select * from Product inner join ProductImage on Product.productIdx = ProductImage.productIdx where imageIdx in (select imageIdx from ProductImage where productIdx in (select productIdx from Product where productAddress = ? and (status = 'Active' or status = 'Reserved') and productName like ?) GROUP BY productIdx) group by Product.productIdx";
//        String getProductsQuery = "select * from Product inner outer join ProductImage where productAddress = ? and (status = 'Active' or status = 'Reserved') and productName like ?";
        String searchWord = '%' + keyword + '%';
        return this.jdbcTemplate.query(getProductsQuery,
                (rs,rowNum) -> new GetProductRes(
                        rs.getInt("productIdx"),
                        rs.getString("productName"),
                        rs.getString("productAddress"),
                        rs.getInt("price"),
                        rs.getString("createdAt"),
                        rs.getString("updatedAt"),
                        rs.getString("image"))
                ,
                productAddress, searchWord);
    }
    //
    public List<GetProductRes> getProductsByKeywordAndCategory(String productAddress, String keyword, String category) {
        String getProductsQuery =
                "select * from Product inner join ProductImage on Product.productIdx = ProductImage.productIdx where imageIdx in (select imageIdx from ProductImage where productIdx in (select productIdx from Product where productAddress = ? and (status = 'Active' or status = 'Reserved') and categoryIdx = (select Category.categoryIdx from Category where categoryName = ?) and productName like ?) GROUP BY productIdx) group by Product.productIdx";
//        String getProductsQuery = "select * from Product inner join ProductImage where productAddress = ? and (status = 'Active' or status = 'Reserved') and categoryIdx = (select Category.categoryIdx from Category where categoryName = ?) and productName like ?";
        String searchWord = '%' + keyword + '%';
        return this.jdbcTemplate.query(getProductsQuery,
                (rs,rowNum) -> new GetProductRes(
                        rs.getInt("productIdx"),
                        rs.getString("productName"),
                        rs.getString("productAddress"),
                        rs.getInt("price"),
                        rs.getString("createdAt"),
                        rs.getString("updatedAt"),
                        rs.getString("image"))
                ,
                productAddress, category, searchWord);
    }

    public List<GetProductDetailRes> getProduct(int productIdx) {
        String getProductQuery = "select * from Product p, User u where p.userIdx = u.userIdx and p.productIdx = ?";
        String getProductImageQuery = "select image from ProductImage where productIdx = ?";
        List<String> images = this.jdbcTemplate.queryForList(getProductImageQuery, String.class, productIdx);

        return this.jdbcTemplate.query(getProductQuery,
                (rs,rowNum) -> new GetProductDetailRes(
                        rs.getString("userName"),
                        rs.getString("productName"),
                        rs.getString("productAddress"),
                        rs.getString("description"),
                        rs.getInt("price"),
                        rs.getString("createdAt"),
                        rs.getString("updatedAt"),
                        rs.getString("status"),
                        images)
                        ,
                productIdx);
    }

    public List<GetSaleProductRes> getSaleProducts(int userIdx) {
//        String getProductsQuery = "select * from Product where userIdx = ?";
        String getProductsQuery =
                "select * from Product inner join ProductImage on Product.productIdx = ProductImage.productIdx where imageIdx in ( select imageIdx from ProductImage where productIdx in (select productIdx from Product where userIdx = ? and (status = 'Active' or status = 'Reserved'))) group by Product.productIdx;";
        return this.jdbcTemplate.query(getProductsQuery,
                (rs,rowNum) -> new GetSaleProductRes(
                        rs.getInt("productIdx"),
                        rs.getString("productName"),
                        rs.getString("productAddress"),
                        rs.getInt("price"),
                        rs.getString("createdAt"),
                        rs.getString("updatedAt"),
                        rs.getString("image")
            )
                ,
                userIdx);
    }

    public List<GetSoldOutProductRes> getSoldOutProducts(int userIdx) {
//        String getProductsQuery =
//                "select * from Product inner join ProductImage on Product.productIdx = ProductImage.productIdx where imageIdx in (select imageIdx from ProductImage where productIdx in (select productIdx from Product where userIdx = ? and (status = 'SoldOut'))) group by Product.productIdx";
        String getProductsQuery =
                "select * from Product inner join ProductImage on Product.productIdx = ProductImage.productIdx where imageIdx in ( select imageIdx from ProductImage where productIdx in (select productIdx from Product where userIdx = ? and (status = 'SoldOut'))) group by Product.productIdx";
        return this.jdbcTemplate.query(getProductsQuery,
                (rs,rowNum) -> new GetSoldOutProductRes(
                        rs.getInt("Product.productIdx"),
                        rs.getString("productName"),
                        rs.getString("productAddress"),
                        rs.getInt("price"),
                        rs.getString("createdAt"),
                        rs.getString("updatedAt"),
                        rs.getString("image"))
                ,
                userIdx);
    }
}
