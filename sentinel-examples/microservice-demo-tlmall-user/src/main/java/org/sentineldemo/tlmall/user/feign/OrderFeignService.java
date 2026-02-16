package org.sentineldemo.tlmall.user.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.tlmall.common.Result;
import org.sentineldemo.tlmall.user.exception.FallbackOrderFeignService;
import org.sentineldemo.tlmall.user.feign.dto.OrderDTO;

// 用OpenFeign声明的客户端，用于调用下游微服务
@FeignClient(
        // 指定下游服务的微服务名
        value = "tlmall-order-sentinel-demo",
        // 下游微服务Rest API的Base Path
        path = "/sentinel-demo",
        // 被流控或者熔断时，返回降级数据的Service类（或使用fallbackFactory属性指定Service工厂）
        fallback = FallbackOrderFeignService.class)
public interface OrderFeignService {
    @GetMapping("/orders")
    Result<?> getOrders(@RequestParam("userId") String userId);

    @GetMapping("/orders/{id}")
    Result<?> getOrderById(@PathVariable("id") Integer id);

    @PostMapping("/orders")
    Result<?> addOrder(@RequestBody OrderDTO orderDTO);
}