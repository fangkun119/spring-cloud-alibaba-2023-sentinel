package org.sentineldemo.nativedemo.exception;

import com.alibaba.csp.sentinel.slots.block.BlockException;


/**
 * 用于处理异常的类
 */
public class ExceptionUtil {
    // 处理流控异常
    public static String handleBlockException(BlockException e){
        // BlockException处理方法
        // 参数与原方法helloWorld一致，或加一个BlockException
        return "流控异常: " +  e.getRule() ;
    }

    // 处理业务异常
    public static String handleFallbackException(Throwable t){
        // 业务异常处理方法
        // 参数与原方法helloWorld一致，或加一个Throwable类型的参数.
        return "业务异常：" + t.getMessage();
    }
}