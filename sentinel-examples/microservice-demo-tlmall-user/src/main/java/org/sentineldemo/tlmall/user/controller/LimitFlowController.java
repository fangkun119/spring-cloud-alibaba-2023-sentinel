package org.sentineldemo.tlmall.user.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class LimitFlowController {

    @RequestMapping("/test")
    public String test() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "========test()========";
    }

    AtomicInteger atomicInteger = new AtomicInteger();

    @RequestMapping("/test2")
    public String test2() {
        atomicInteger.getAndIncrement();
        if (atomicInteger.get() % 2 == 0){
            //模拟异常和异常比率
            int i = 1/0;
        }

        return "========test2()========";
    }

//    public String handleException(BlockException exception) {
//        return "===被限流降级啦===";
//    }




}
