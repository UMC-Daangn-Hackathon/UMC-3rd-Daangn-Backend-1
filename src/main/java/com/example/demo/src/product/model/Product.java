package com.example.demo.src.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Product {
    private int productIdx;
    private String productName;
    private int userIdx;
    private int categoryIdx;
    private String productAddress;
    private String description;
    private int price;
//    private String images;
}