package org.example.service;

import org.example.product.bean.Product;
import org.springframework.stereotype.Service;


public interface ProductService {
    Product getProductById(Long productId);


}
