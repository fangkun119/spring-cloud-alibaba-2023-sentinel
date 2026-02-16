package org.sentineldemo.tlmall.order.controller;


import com.alibaba.druid.util.StringUtils;
import io.netty.util.internal.StringUtil;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tlmall.common.BusinessException;
import org.tlmall.common.Result;
import org.sentineldemo.tlmall.order.dto.OrderDTO;
import org.sentineldemo.tlmall.order.service.OrderService;

import java.rmi.UnexpectedException;

@RestController
@RequestMapping("/sentinel-demo")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public Result<?> getOrders(
            @NotNull
            @RequestParam("userId")
            String userId) {
        try {
            // 用于模拟下游频繁异常，触发上游用户服务Sentinel熔断
            if (StringUtils.equalsIgnoreCase(userId, "illegal")) {
                throw new RuntimeException("uncatch exception triggered");
            }
            // 获取属于某用户的订单
            log.info("根据userId:" + userId + "查询订单信息");
            return orderService.getOrderByUserId(userId);
        } catch (BusinessException e) {
            return Result.failed(e.getMessage());
        }
    }

    @GetMapping("/orders/{id}")
    public Result<?> getOrderById(
            @PathVariable("id") Integer id) {
        try {
            return orderService.getOrderById(id);
        } catch (BusinessException e) {
            return Result.failed(e.getMessage());
        }
    }

    @PostMapping("/orders")
    public Result<?> addOrder(@RequestBody OrderDTO orderDTO) {
        return Result.success(orderDTO);
    }

    /**
     @PostMapping("/post2") public Result<?>  post2(@RequestBody OrderDTO orderDTO,@RequestParam("token") String token){
     log.info("token:"+token);
     return Result.success(orderDTO);
     }

     @PostMapping(value = "/post3/{userId}")
     public Result<?> post3(@RequestBody OrderDTO orderDTO, @PathVariable("userId") String userId) {
     return Result.success(orderDTO);
     }
     */
}
