package com.example.demo.src.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetProductDetailRes {
    private String userName;
    private String productName;
    private String productAddress;
    private int price;
    private String createdAt;
    private String updatedAt;
    private String status;
    private List<String> productImage;
}