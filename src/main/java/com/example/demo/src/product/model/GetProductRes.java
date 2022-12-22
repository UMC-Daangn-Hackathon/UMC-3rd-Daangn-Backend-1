package com.example.demo.src.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetProductRes {
    private String productName;
    private String productAddress;
    private int price;
    private String createdAt;
    private String updatedAt;
    private String productImage;
}
