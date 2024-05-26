package com.example.pricecompareredis.service;

import com.example.pricecompareredis.vo.Product;
import com.example.pricecompareredis.vo.ProductGrp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class LowestPriceServiceImpl implements LowestPriceService {

    @Autowired
    private RedisTemplate myProductPriceRedis;

    public Set getZsetValue(String key) {
        Set myTempSet = new HashSet();
        myTempSet = myProductPriceRedis.opsForZSet().rangeWithScores(key, 0, 9);

        return myTempSet;
    }

    public int setNewProduct(Product newProduct) {
        int rank = 0;
        myProductPriceRedis.opsForZSet().add(newProduct.getProdGrpId(), newProduct.getProductId(), newProduct.getPrice());
        rank = myProductPriceRedis.opsForZSet().rank(newProduct.getProdGrpId(), newProduct.getProductId()).intValue();

        return rank;
    }

    public int setNewProductGrp(ProductGrp newProductGrp) {
        List<Product> product = newProductGrp.getProductList();
        String productId = product.get(0).getProductId();
        int price = product.get(0).getPrice();
        myProductPriceRedis.opsForZSet().add(newProductGrp.getProdGrpId(), productId, price);
        int productCnt = myProductPriceRedis.opsForZSet().zCard(newProductGrp.getProdGrpId()).intValue();

        return productCnt;
    }
}
