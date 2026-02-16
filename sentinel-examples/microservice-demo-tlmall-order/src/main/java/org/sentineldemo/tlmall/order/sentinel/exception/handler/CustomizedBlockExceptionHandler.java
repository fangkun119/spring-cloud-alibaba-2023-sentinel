package org.sentineldemo.tlmall.order.sentinel.exception.handler;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.tlmall.common.Result;

@Slf4j
@Component
public class CustomizedBlockExceptionHandler implements BlockExceptionHandler {
    @Override
    public void handle(
            HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
        log.info("BlockException：", e.getRule());

        Result result = null;
        if (e instanceof FlowException) {
            result = Result.failed("接口限流");
        } else if (e instanceof DegradeException) {
            result = Result.failed("服务降级");
        } else if (e instanceof ParamFlowException) {
            result = Result.failed("热点参数限流");
        } else if (e instanceof SystemBlockException) {
            result = Result.failed("触发系统保护规则");
        } else if (e instanceof AuthorityException) {
            result = Result.failed("授权规则不通过");
        }

        // 返回json数据
        response.setStatus(500);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(
                response.getWriter(), result);
    }
}