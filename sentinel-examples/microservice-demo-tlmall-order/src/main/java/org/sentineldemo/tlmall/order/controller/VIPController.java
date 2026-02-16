package org.sentineldemo.tlmall.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tlmall.common.BusinessException;
import org.tlmall.common.Result;
import org.sentineldemo.tlmall.order.service.OrderService;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/sentinel-demo/vip")
public class VIPController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/orders/{id}")
    public Result<?> getOrder(@PathVariable("id") Integer id){
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
