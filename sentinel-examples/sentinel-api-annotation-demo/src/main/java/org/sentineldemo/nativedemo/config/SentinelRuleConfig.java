package org.sentineldemo.nativedemo.config;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.sentineldemo.nativedemo.controller.SentinelAPIDemo;
import org.sentineldemo.nativedemo.controller.SentinelAnnotationClassHandlerDemo;
import org.sentineldemo.nativedemo.controller.SentinelAnnotationMethodHandlerDemo;

import java.util.ArrayList;
import java.util.List;

@Component
public class SentinelRuleConfig {
    // 定义流控规则
    @PostConstruct
    public static void initFlowRules() {
        List<FlowRule> rules = new ArrayList<>();
        // REST API: "/sentinel/api-demo/hello-world"
        rules.add(getQPSRule(
                SentinelAPIDemo.RESOURCE_NAME_HELLOW_WORLD, 1));
        // REST API: "/sentinel/annotation/method-handler-demo/hello-world"
        rules.add(getQPSRule(
                SentinelAnnotationMethodHandlerDemo.RESOURCE_NAME_HELLO_WORLD, 1));
        // REST API: "/sentinel/annotation/class-handler-demo/hello-world"
        rules.add(getQPSRule(
                SentinelAnnotationClassHandlerDemo.RESOURCE_NAME_HELLO_WORLD, 1));
        FlowRuleManager.loadRules(rules);
    }

    // 辅助方法
    private static FlowRule getQPSRule(final String RESOURCE_NAME, final int QPS) {
        // 构建Rule
        FlowRule rule = new FlowRule();
        // 受保护的资源
        rule.setResource(RESOURCE_NAME);
        // 规则内容
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(QPS);
        // 返回
        return rule;
    }
}
