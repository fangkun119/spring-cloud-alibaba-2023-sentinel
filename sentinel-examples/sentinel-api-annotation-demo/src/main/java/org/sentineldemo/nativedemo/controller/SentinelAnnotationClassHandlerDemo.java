package org.sentineldemo.nativedemo.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.sentineldemo.nativedemo.exception.ExceptionUtil;

import static org.sentineldemo.nativedemo.common.APIConstants.HELLO_WORLD;
import static org.sentineldemo.nativedemo.common.APIConstants.SENTINEL_ANNOTATION_CLASS_HANDLER_DEMO;


@RestController
@Slf4j
@RequestMapping(SENTINEL_ANNOTATION_CLASS_HANDLER_DEMO)
public class SentinelAnnotationClassHandlerDemo {
    // REST API：/sentinel/annotation/class-handler-demo
    public static final String RESOURCE_NAME_HELLO_WORLD
            = SENTINEL_ANNOTATION_CLASS_HANDLER_DEMO + HELLO_WORLD;

    @SentinelResource(value = RESOURCE_NAME_HELLO_WORLD,
            blockHandler = "handleBlockException", blockHandlerClass = ExceptionUtil.class,
            fallback = "handleFallbackException", fallbackClass = ExceptionUtil.class)
    @RequestMapping(HELLO_WORLD)
    public String helloWorld() {
        // 触发业务异常
        int i = 1 / 0;
        return "hello world ";
    }
}
