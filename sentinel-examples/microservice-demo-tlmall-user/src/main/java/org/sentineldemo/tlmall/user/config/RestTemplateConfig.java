package org.sentineldemo.tlmall.user.config;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.sentineldemo.tlmall.user.exception.ExceptionUtil;

@Configuration
public class RestTemplateConfig {
    @Bean
    @LoadBalanced
    @SentinelRestTemplate(
            blockHandler = "handleBlockException",
            blockHandlerClass = ExceptionUtil.class,
            fallback = "handleFallback",
            fallbackClass = ExceptionUtil.class
    )
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
