package org.example.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

//注解自动装配
@Component
public class XTokenRequestInterceptor implements RequestInterceptor {
    /**
     * 请求拦截器
     * @param template 请求模板
     */
    @Override
    public void apply(RequestTemplate template) {
        System.out.println("拦截器启动");
        template.header("X-Token", UUID.randomUUID().toString());
    }
}
