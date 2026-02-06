package org.example.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "order")//配置批量绑定在nacos下无需@RefreshScope实现自动刷新
@Data
public class OrderProperties {
    String timeout;
    //短横线映射驼峰
    String autoConfirm;

    String dbUrl;
}
