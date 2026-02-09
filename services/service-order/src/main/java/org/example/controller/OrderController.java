package org.example.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.example.order.bean.Order;
import org.example.properties.OrderProperties;
import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@Slf4j
@RestController
//@RefreshScope
//@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

//    @Value("${order.timeout}")
//    String orderTimeout;
//    @Value("${order.auto-confirm}")
//    String orderAutoConfirm;

    @Autowired
    OrderProperties orderProperties;

    @GetMapping("/config")
    public String config() {
        return "orderTimeout:"+orderProperties.getTimeout()+",orderAutoConfirm:"+orderProperties.getAutoConfirm()+",DBurl:"+orderProperties.getDbUrl();
    }

    @GetMapping("/create")
    public Order createOrder(@RequestParam("userId") Long userId,
                             @RequestParam("productId") Long productId) {
        Order order =orderService.createOrder(productId, userId);
        return order;
    }

    @SentinelResource(value = "seckill-order",fallback = "seckillFallBack")
    @GetMapping("/secKill")
    public Order secKill(@RequestParam("userId") Long userId,
                             @RequestParam("productId") Long productId) {
        Order order =orderService.createOrder(productId, userId);
        order.setId(Long.MAX_VALUE);
        return order;
    }
    public Order seckillFallBack( Long userId,
                              Long productId,
                                 Throwable exception) {
        System.out.println("seckill兜底回调");
        Order order =new Order();
        order.setId(productId);
        order.setUserId(userId);
        order.setAddress(exception.getClass().getSimpleName());
        return order;
    }


    @GetMapping("/writeDb")
    public String writeDb(){
        return "write success";
    }

    @GetMapping("readDb")
    public String readDb(){
        return "read success";
    }

}
