package com.example.pricecompareredis.service;

import com.example.pricecompareredis.vo.Keyword;
import com.example.pricecompareredis.vo.Product;
import com.example.pricecompareredis.vo.ProductGrp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public int setNewProductGrpToKeyword(String keyword, String prodGrpId, double score) {
        int rank = 0;
        myProductPriceRedis.opsForZSet().add(keyword, prodGrpId, score);
        rank = myProductPriceRedis.opsForZSet().rank(keyword, prodGrpId).intValue();

        return rank;
    }

    @Override
    public Keyword getLowestPriceProductByKeyword(String keyword) {
        Keyword returnInfo = new Keyword();
        List<ProductGrp> returnProdGrp = new ArrayList<>();

        // keyword 를 통해 productGroup 가져오기 (10개)
        returnProdGrp = getProdGrpUsingKeyword(keyword);



        // 가져온 정보들을 return 할 object에 넣기

        // 해당 object return

        return returnInfo;
    }

    // keyword 로 productGroup 가져오는 기능
    public List<ProductGrp> getProdGrpUsingKeyword(String keyword) {
        List<ProductGrp> returnInfo = new ArrayList<>();
        ProductGrp tempProdGrp = new ProductGrp();

        // input 받은 keyword 로 prodGrpId 조회
        List<String> prodGrpIdList = new ArrayList<>();
        prodGrpIdList = List.copyOf(myProductPriceRedis.opsForZSet().rangeWithScores(keyword, 0, 9));

        // loop 타면서 productGroup 으로 Product:price 가져오기 (10개)
        for (final String prodGrpId : prodGrpIdList) {
            Set prodAndPriceList = new HashSet();
            prodAndPriceList = myProductPriceRedis.opsForZSet().rangeWithScores(prodGrpId, 0, 9);
        }

        return returnInfo;
    }
}
