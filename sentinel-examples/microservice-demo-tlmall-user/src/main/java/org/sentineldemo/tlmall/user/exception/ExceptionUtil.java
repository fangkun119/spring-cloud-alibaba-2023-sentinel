package org.sentineldemo.tlmall.user.exception;

import com.alibaba.cloud.sentinel.rest.SentinelClientHttpResponse;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.tlmall.common.Result;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class ExceptionUtil {

    public static SentinelClientHttpResponse handleBlockException(
            HttpRequest request,
            byte[] body,
            ClientHttpRequestExecution execution,
            BlockException e) {
        Result result = Result.failed("流控异常：" + e.getRule());
        try {
            return new SentinelClientHttpResponse(new ObjectMapper().writeValueAsString(result));
        } catch (JsonProcessingException jsonException) {
            return getJsonProcessingFailResponse(jsonException);
        }
    }

    public static SentinelClientHttpResponse handleFallback(
            HttpRequest request,
            byte[] body,
            ClientHttpRequestExecution execution,
            BlockException e) {
        Result result = Result.failed("链路熔断：" + e.getRule());
        try {
            return new SentinelClientHttpResponse(new ObjectMapper().writeValueAsString(result));
        }  catch (JsonProcessingException jsonException) {
            return getJsonProcessingFailResponse(jsonException);
        }
    }

    private static SentinelClientHttpResponse getJsonProcessingFailResponse(
            JsonProcessingException e) {
        log.error("Json处理异常", e);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 500);
        result.put("message", "Json处理异常：" + (Objects.isNull(e) ? "" : e.getMessage()));
        return new SentinelClientHttpResponse(JSON.toJSONString(result));
    }
}
