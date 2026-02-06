package org.example.service.impl;

import org.example.product.bean.Product;
import org.example.service.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductServiceImpl implements ProductService {
    @Override
    public Product getProductById(Long productId) {
        Product product = new Product();
        product.setProductName("name");
        product.setId(productId);
        product.setNum(123);
        product.setPrice(new BigDecimal(12.34));

        return product;
    }
}
