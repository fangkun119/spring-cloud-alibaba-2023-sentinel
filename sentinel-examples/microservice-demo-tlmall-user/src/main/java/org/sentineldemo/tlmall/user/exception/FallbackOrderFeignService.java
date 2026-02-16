package org.sentineldemo.tlmall.user.exception;

import org.springframework.stereotype.Component;
import org.tlmall.common.Result;
import org.sentineldemo.tlmall.user.feign.OrderFeignService;
import org.sentineldemo.tlmall.user.feign.dto.OrderDTO;

//必须用@Component交给spring管理
@Component
public class FallbackOrderFeignService implements OrderFeignService {
    @Override
    public Result getOrders(String userId) {
        return Result.failed("FeignClient返回降级数据");
    }

    @Override
    public Result<?> getOrderById(Integer id) {
        return Result.failed( "FeignClient返回降级数据" );
    }

    @Override
    public Result<?> addOrder(OrderDTO orderDTO) {
        return Result.failed( "FeignClient返回降级数据" );
    }
}