package com.example.demo.src.product;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.product.model.PostProductReq;
import com.example.demo.src.product.model.PostProductRes;
import com.example.demo.src.user.UserProvider;
import com.example.demo.src.user.UserService;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/app/products")
public class ProductController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

//    @Autowired
//    private final ProductProvider productProvider ;

    @Autowired
    private final ProductService productService;
    @Autowired
    private final JwtService jwtService;

    public ProductController(ProductService productService, JwtService jwtService) {
//        this.productProvider = productProvider;
        this.productService = productService;
        this.jwtService = jwtService;
    }

    @ResponseBody
    @PostMapping("/post")
    public BaseResponse<PostProductRes> createPost(@RequestBody PostProductReq postProductReq)  {
        try{
        PostProductRes postProductRes = productService.createPost(postProductReq);
        return new BaseResponse<>(postProductRes);
    }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}

