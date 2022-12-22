package com.example.demo.src.product;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.product.model.GetProductRes;
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

    @Autowired
    private final ProductProvider productProvider;

    @Autowired
    private final ProductService productService;
    @Autowired
    private final JwtService jwtService;

    public ProductController(ProductProvider productProvider, ProductService productService, JwtService jwtService) {
        this.productProvider = productProvider;
        this.productService = productService;
        this.jwtService = jwtService;
    }

    /**
     * 상품 조회 API
     * [GET] /products/:productAddress
     * @return BaseResponse<List<GetProductRes>>
     */
    @ResponseBody
    @GetMapping("/{productAddress}") // (GET) 127.0.0.1:9000/app/products
    public BaseResponse<List<GetProductRes>> getProducts(@PathVariable("productAddress") String productAddress) {
        try{
            // Get Products
            List<GetProductRes> getProductsRes = productProvider.getProducts(productAddress);
            return new BaseResponse<>(getProductsRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
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

