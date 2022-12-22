package com.example.demo.src.product;

import com.example.demo.config.BaseException;
import com.example.demo.src.product.model.GetProductRes;
import com.example.demo.src.user.UserDao;
import com.example.demo.utils.JwtService;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class ProductProvider {
    private ProductDao productDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ProductProvider(ProductDao productDao) {
        this.productDao = productDao;
    }


    public List<GetProductRes> getProducts(String productAddress) throws BaseException {
        try {
            List<GetProductRes> getProductRes = productDao.getProducts(productAddress);
            return getProductRes;

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetProductRes> getProductsByCategory(String productAddress, String category) throws BaseException {
        try {
            List<GetProductRes> getProductRes = productDao.getProductsByCategory(productAddress, category);
            return getProductRes;

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

//    public List<GetProductRes> getProductsByKeyword(String productAddress, String keyword) throws BaseException {
//        try {
//            List<GetProductRes> getProductRes = productDao.getProductsByKeyword(productAddress, keyword);
//            return getProductRes;
//
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }
//
//    public List<GetProductRes> getProductsByKeywordAndCategory(String productAddress, String keyword, String category) throws BaseException {
//        try {
//            List<GetProductRes> getProductRes = productDao.getProductsByKeywordAndCategory(productAddress, keyword, category);
//            return getProductRes;
//
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }
}

