package org.example.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.example.feign.ProductFeignClient;
import org.example.order.bean.Order;
import org.example.product.bean.Product;
import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    DiscoveryClient discoveryClient;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    LoadBalancerClient loadBalancerClient;
    @Autowired
    ProductFeignClient productFeignClient;


    @SentinelResource(value = "createOrder",blockHandler = "createOrderFallBack")
    @Override
    public Order createOrder(Long productId, Long userId) {
        //远程获取商品数据
       // Product product = getProductFromRemote(productId);
        Product product =productFeignClient.getProductById(productId);

        Order order = new Order();
        order.setUserId(userId);
        order.setAddress("america");
        order.setId(1l);
        order.setNickName("baiyaoshi");
        order.setProductList(Arrays.asList(product));
        order.setTotalAmount(product.getPrice().multiply(new BigDecimal(product.getNum())));
        return order;
    }

    //兜底回调
    public Order createOrderFallBack(Long productId, Long userId, BlockException e) {
        Order order = new Order();
        order.setUserId(userId);
        order.setAddress("异常信息"+e.getClass().getSimpleName());
        order.setId(1l);
        order.setNickName("未知用户");
        order.setTotalAmount(new BigDecimal("0"));
        order.setProductList(null);
        return order;
    }


    //远程获取订单
    private Product getProductFromRemote(Long productId){
        //获取商品服务所有ip+端口
        //改为负载均衡
        //List<ServiceInstance> instances= discoveryClient.getInstances("service-product");
        //ServiceInstance instance=instances.get(0);
        ServiceInstance instance = loadBalancerClient.choose("service-product");
        //String url= "http://" +instance.getHost()+":"+instance.getPort()+"/product/"+productId;
        String url= "http://service-product/product/"+productId;
        log.info("url:{}", url);
        //给远程发送请求
        Product product=restTemplate.getForObject(url, Product.class);
        return product;

    }
}
