package com.example.demo.src.product;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.product.model.*;
import com.example.demo.src.user.model.GetUserRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/app/products")
public class ProductController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final ProductProvider productProvider ;

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
     * 검색어로 상품 조회 API
     * [GET] /products/:productAddress? keyword=
     *
     * 카테고리로 상품 조회 API
     * [GET] /products/:productAddress? category=
     *
     * 검색어, 카테고리로 상품 조회 API
     * [GET] /products/:productAddress? keyword= & category=
     */
    @ResponseBody
    @GetMapping("/{productAddress}") // (GET) 127.0.0.1:9000/app/products/:productAddress
    public BaseResponse<List<GetProductRes>> getProducts(@PathVariable("productAddress") String productAddress, @RequestParam(required = false) String keyword, @RequestParam(required = false) String category) {
        try{
            if (keyword == null && category == null) {
                // Get Products
                List<GetProductRes> getProductsRes = productProvider.getProducts(productAddress);
                return new BaseResponse<>(getProductsRes);
            } else if (keyword == null) {
                List<GetProductRes> getProductsRes = productProvider.getProductsByCategory(productAddress, category);
                return new BaseResponse<>(getProductsRes);
            }
            else if (category == null) {
                List<GetProductRes> getProductsRes = productProvider.getProductsByKeyword(productAddress, keyword);
                return new BaseResponse<>(getProductsRes);
            }
            else {
                List<GetProductRes> getProductsRes = productProvider.getProductsByKeywordAndCategory(productAddress, keyword, category);
                return new BaseResponse<>(getProductsRes);
            }

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



    /**
     * 상품 상세 조회 조회 API
     * [GET] /products/:productIdx
     * @return BaseResponse<GetProductRes>
     */
    @ResponseBody
    @GetMapping("/detail")
    public BaseResponse<List<GetProductDetailRes>> getProductDetail(@RequestParam(required = true) int productIdx) {
        try {
            List<GetProductDetailRes> getProductDetailRes = productProvider.getProduct(productIdx);
            return new BaseResponse<>(getProductDetailRes);
        } catch(BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 마이페이지 판매중 상품 조회 API
     * [GET] /products/sale/
     * @return BaseResponse<GetSaleProductRes>
     */
    @ResponseBody
    @GetMapping("/sale")
    public BaseResponse<List<GetSaleProductRes>> getSaleProduct(@RequestParam(required = true) int userIdx) {
        try {
            List<GetSaleProductRes> getSaleProductRes = productProvider.getSaleProduct(userIdx);
            return new BaseResponse<>(getSaleProductRes);
        } catch(BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 마이페이지 판매 완료 상품 조회 API
     * [GET] /products/soldout/
     * @return BaseResponse<GetSoldOutProductRes>
     */
    @ResponseBody
    @GetMapping("/soldout")
    public BaseResponse<List<GetSoldOutProductRes>> getSoldOutProduct(@RequestParam(required = true) int userIdx) {
        try {
            List<GetSoldOutProductRes> getSoldOutProductRes = productProvider.getSoldOutProduct(userIdx);
            return new BaseResponse<>(getSoldOutProductRes);
        } catch(BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}

