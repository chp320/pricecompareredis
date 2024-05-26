package com.example.pricecompareredis.controller;

import com.example.pricecompareredis.service.LowestPriceService;
import com.example.pricecompareredis.vo.Product;
import com.example.pricecompareredis.vo.ProductGrp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/")
public class LowestPriceController {

    @Autowired
    private LowestPriceService myLowestPriceService;

    @GetMapping("/getZSETValue")
    public Set getZsetValue(String key) {
        return myLowestPriceService.getZsetValue(key);
    }

    @PutMapping("/product")
    public int setNewProduct(@RequestBody Product newProduct) {
        return myLowestPriceService.setNewProduct(newProduct);
    }

    @PutMapping("/productGroup")
    public int setNewProduct(@RequestBody ProductGrp newProductGrp) {
        return myLowestPriceService.setNewProductGrp(newProductGrp);
    }
}
