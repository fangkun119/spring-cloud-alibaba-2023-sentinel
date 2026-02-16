package org.sentineldemo.nativedemo.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.sentineldemo.nativedemo.common.APIConstants.HELLO_WORLD;
import static org.sentineldemo.nativedemo.common.APIConstants.SENTINEL_ANNOTATION_METHOD_HANDLER_DEMO;

@RestController
@Slf4j
@RequestMapping(SENTINEL_ANNOTATION_METHOD_HANDLER_DEMO)
public class SentinelAnnotationMethodHandlerDemo {
    // REST API: /sentinel/annotation/method-handler-demo/hello-world
    public static final String RESOURCE_NAME_HELLO_WORLD
            = SENTINEL_ANNOTATION_METHOD_HANDLER_DEMO + HELLO_WORLD;

    @SentinelResource(
            value = RESOURCE_NAME_HELLO_WORLD,
            blockHandler = "handleBlockException", // 不指定blockHandlerClass
            fallback = "handleFallbackExcewption"  // 不指定fallbackClass
    )
    @RequestMapping(HELLO_WORLD)
    public String helloWorld() {
        // 触发业务异常
        int i = 1 / 0;
        return "helloworld ";
    }

    public String handleBlockException(BlockException e) {
        // BlockException处理方法
        // 参数与原方法helloWorld一致，或加一个BlockException
        return "流控异常: " +  e.getRule() ;
    }

    public String handleFallbackExcewption(Throwable t) {
        // Fallback异常处理函数
        // 参数与原方法参数与helloWorld一致一致，或加一个Throwable类型的参数.
        return "业务异常：" + t.getMessage();
    }
}
