package org.sentineldemo.tlmall.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.tlmall.common.Result;
import org.sentineldemo.tlmall.user.feign.OrderFeignService;

import java.util.Objects;

@RestController
@RequestMapping("/sentinel-demo/users")
@Slf4j
public class UserController{

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OrderFeignService orderService;

    @RequestMapping(value = "/{userId}/orders")
    public Result<?> getOrderByUserId(
            @PathVariable("userId") String userId,
            @RequestParam(value = "useOpenFeign", required = false) Boolean useOpenFeign) {
        log.info("根据userId:" + userId + "查询订单信息");
        if (Objects.isNull(useOpenFeign) || !useOpenFeign) {
            // 方法1: RestTemplate调用下游，它被两个注解标注过
            // * @LoadBalanced - 整合了负载均衡和Nacos服务名解析
            // * @SentinelRestTemplate - 整合了Sentinel流量保护
            String url = "http://tlmall-order-sentinel-demo/sentinel-demo/orders?userId=" + userId;
            return restTemplate.getForObject(url, Result.class);
        } else {
            // 方法2：使用openFeign调用订单服务
            return orderService.getOrders(userId);
        }
    }
}

