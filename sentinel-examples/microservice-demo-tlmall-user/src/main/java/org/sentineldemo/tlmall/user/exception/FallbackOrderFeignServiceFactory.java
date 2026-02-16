package org.sentineldemo.tlmall.user.exception;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.tlmall.common.Result;
import org.sentineldemo.tlmall.user.feign.OrderFeignService;
import org.sentineldemo.tlmall.user.feign.dto.OrderDTO;

@Component
public class FallbackOrderFeignServiceFactory implements FallbackFactory<OrderFeignService> {
    @Override
    public OrderFeignService create(Throwable throwable) {
        return new OrderFeignService() {
            @Override
            public Result<?> getOrders(String userId) {
                return Result.failed( "FeignClient返回降级数据" );
            }

            @Override
            public Result<?> getOrderById(Integer id) {
                return Result.failed( "FeignClient返回降级数据" );
            }

            @Override
            public Result<?> addOrder(OrderDTO orderDTO) {
                return Result.failed( "FeignClient返回降级数据" );
            }
        };
    }
}