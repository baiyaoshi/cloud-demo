package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;

import java.util.List;

@SpringBootTest
class LoadBalancerTest {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Test
void test() {
    for (int i = 0; i < 5; i++) {
        // 1. 先只调用一次 choose，拿到确定的实例
        ServiceInstance instance = loadBalancerClient.choose("service-order");

        // 2. 打印这个实例的 Host 和 Port
        if (instance != null) {
            System.out.println(instance.getHost() + ":" + instance.getPort());
        }
    }
}
}
