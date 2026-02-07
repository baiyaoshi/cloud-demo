package org.example.config;

import feign.Logger;
import feign.Retryer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OrderConfig {
    @Bean
    @LoadBalanced
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }

    //@Bean
    Retryer retryer(){
        return new Retryer.Default();
    }

}
