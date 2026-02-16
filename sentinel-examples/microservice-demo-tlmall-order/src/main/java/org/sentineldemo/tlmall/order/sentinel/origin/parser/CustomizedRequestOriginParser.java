package org.sentineldemo.tlmall.order.sentinel.origin.parser;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomizedRequestOriginParser implements RequestOriginParser {
    /**
     * 通过request获取来源标识，交给授权规则或来源流控规则进行匹配
     */
    @Override
    public String parseOrigin(HttpServletRequest request) {
        // 从请求参数中获取 userId 作为来源标识
        String origin = request.getParameter("userId");
        log.info("origin: {}", origin);
        // 返回来源标识，返回 null 表示不限制
        return origin;
    }
}