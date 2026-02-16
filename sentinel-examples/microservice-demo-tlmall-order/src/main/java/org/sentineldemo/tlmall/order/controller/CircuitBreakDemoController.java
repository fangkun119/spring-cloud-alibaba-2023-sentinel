package org.sentineldemo.tlmall.order.controller;

import org.sentineldemo.tlmall.order.dto.OrderDTO;
import org.sentineldemo.tlmall.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tlmall.common.BusinessException;
import org.tlmall.common.Result;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/sentinel-demo/circuit-breaker")
public class CircuitBreakDemoController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/slow-request/orders")
    public Result<List<OrderDTO>> slowRequest() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Result.success(Collections.emptyList());
    }

    AtomicInteger atomicInteger = new AtomicInteger(0);
    @GetMapping("/exception-prone/orders")
    public Result<List<OrderDTO>> exceptionProneRequest() {
        atomicInteger.getAndIncrement();
        if (atomicInteger.get() % 2 == 0){
            //模拟异常和异常比率
            int i = 1/0;
        }
        return Result.success(Collections.emptyList());
    }

    @GetMapping("/test2")
    public Result<?> test2(
            @PathVariable("id") Integer id){
        Result<?> res = null;
        try {
            res = orderService.getOrderById(id);
        }
        catch (BusinessException e) {
            return Result.failed(e.getMessage());
        }
        return res;
    }
}
