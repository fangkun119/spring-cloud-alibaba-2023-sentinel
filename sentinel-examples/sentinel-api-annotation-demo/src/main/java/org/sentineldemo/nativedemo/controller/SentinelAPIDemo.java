package org.sentineldemo.nativedemo.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.sentineldemo.nativedemo.common.APIConstants.SENTINEL_API_DEMO;
import static org.sentineldemo.nativedemo.common.APIConstants.HELLO_WORLD;


/**
 * 用Sentinel API来保护资源
 */
@RestController
@Slf4j
@RequestMapping(SENTINEL_API_DEMO)
public class SentinelAPIDemo {
    // REST API : /sentinel/api-demo/hello-world
    public static final String RESOURCE_NAME_HELLOW_WORLD = SENTINEL_API_DEMO + HELLO_WORLD;

    @RequestMapping(value = HELLO_WORLD)
    public String hello() {
        // 受保护的资源: ”/sentinel/api-demo/hello-world"
        try (Entry entry = SphU.entry(RESOURCE_NAME_HELLOW_WORLD)) {
            // 受保护的业务逻辑
            log.info("hello world");
            return "hello world";
        } catch (BlockException e) {
            // Sentinel流控时抛出此异常
            log.info("blocked!");
            return "流控异常：" + e.getRule();
        } catch (Throwable e) {
            // 捕获业务代码抛出的异常
            log.info("exception");
            return "处理异常：" + e.getMessage();
        }
    }
}