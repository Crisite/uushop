package com.puff.feign;

import com.puff.entity.ProductInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.math.BigDecimal;

@FeignClient("product-service")
public interface ProductFeign {
    @GetMapping("/buyer/product/findPriceById/{id}")
    BigDecimal findPriceById(@PathVariable("id")int id);
    @GetMapping("/buyer/product/findById/{id}")
    ProductInfo findById(@PathVariable("id")int id);
    @PutMapping("/buyer/product/subStockById/{id}/{quantity}")
    Boolean subStockById(@PathVariable("id") Integer id, @PathVariable("quantity") Integer quantity);
}
