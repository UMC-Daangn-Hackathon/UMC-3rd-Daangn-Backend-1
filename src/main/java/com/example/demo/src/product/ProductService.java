package com.example.demo.src.product;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.product.model.PostProductReq;
import com.example.demo.src.product.model.PostProductRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ProductService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ProductDao productDao;
    private final JwtService jwtService;

    @Autowired
    public ProductService(ProductDao productDao, JwtService jwtService) {
        this.productDao = productDao;
        this.jwtService = jwtService;
    }


    public PostProductRes createPost(PostProductReq postProductReq) throws BaseException{
        if(postProductReq.getProductName() == null){
            throw new BaseException(BaseResponseStatus.POST_PRODUCT_EXISTS_NAME);
        }
        int userIdx = postProductReq.getUserIdx();
        int userIdxByJwt = jwtService.getUserIdx();
        if(userIdx != userIdxByJwt){
            throw new BaseException(BaseResponseStatus.INVALID_USER_JWT);
        }
        try{
            int productIdx = productDao.createPost(postProductReq);
            return new PostProductRes(productIdx);
        }catch (Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

}
