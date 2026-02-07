package org.example.feign;

import org.example.feign.fallback.ProductFeignClientFallBack;
import org.example.product.bean.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-product",fallback = ProductFeignClientFallBack.class)//自动调用微服务
public interface ProductFeignClient {
    //mvc注解两套使用逻辑
    //标注在Controller是接受这样的请求
    //标注在FeignClient是发送这样的请求
    @GetMapping("/product/{id}")
    Product getProductById(@PathVariable("id") Long id);
}
