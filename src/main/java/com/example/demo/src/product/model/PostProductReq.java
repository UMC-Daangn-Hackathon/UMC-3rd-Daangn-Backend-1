package com.example.demo.src.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostProductReq {
    private String productName;
    private int userIdx;
    private int categoryIdx;
    private String productAddress;
    private String description;
    private int price;
//    private String images;
}
