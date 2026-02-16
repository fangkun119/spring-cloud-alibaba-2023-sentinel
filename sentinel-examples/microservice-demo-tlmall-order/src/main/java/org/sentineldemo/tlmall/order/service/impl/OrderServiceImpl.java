/*
 * Copyright 2013-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sentineldemo.tlmall.order.service.impl;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tlmall.common.Result;
import org.sentineldemo.tlmall.order.entity.Order;
import org.sentineldemo.tlmall.order.mapper.OrderMapper;
import org.sentineldemo.tlmall.order.service.OrderService;

import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @SentinelResource(value = "getOrderById", blockHandler = "handleException")
    @Override
    public Result<?> getOrderById(Integer id) {
        Order order = orderMapper.getOrderById(id);
        return Result.success(order);
    }

    @Override
    public Result<?> getOrderByUserId(String userId) {
        List<Order> list = orderMapper.getOrderByUserId(userId);
        return Result.success(list);
    }

    public Result handleException(Integer id, BlockException ex) {
        return Result.failed("getOrderById被限流降级");
    }
}
