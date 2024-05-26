package com.example.pricecompareredis.vo;

import lombok.Data;

import java.util.List;

@Data
public class ProductGrp {

    private String prodGrpId;   // FPG0001  --> 상품 자체
    private List<Product> productList;  // [{d1fc1031-da1c-40da-9cd1-e9fef3f2a336, 25000}, {}...}]  --> seller 가 판매하는 '상품자체' 목록
}
