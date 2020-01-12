package com.jhipster.common.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * 增加对GET方法传递动态POJO参数的支持，将参数映射到URL中<br/>
 * *注意使用{@link SpringQueryMap}标记，便于后续查找替换<br/>
 * spring-cloud-openfeign升级到2.1.0. RC1版本后可用官方的@SpringQueryMap替换
 * 
 * @author yuanke
 * @date 2019/9/13 1:13
 */
@Component
public class QueryMapRequestInterceptor implements RequestInterceptor {

    private final Logger log = LoggerFactory.getLogger(QueryMapRequestInterceptor.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void apply(RequestTemplate template) {
        // feign不支持GET方法的POJO映射为URL参数，手动转换，json body转query
        if (template.method().equals(HttpMethod.GET.name()) && template.body() != null) {
            try {
                // 获取GET的body数据
                JsonNode jsonNode = objectMapper.readTree(template.body());
                template.body(null);

                // 拼接为URL参数
                Map<String, Collection<String>> queries = new HashMap<>();
                buildQuery(jsonNode, "", queries);
                template.queries(queries);
            } catch (IOException e) {
                log.error("GET请求拼接动态参数异常", e);
                throw new RuntimeException(e);
            }
        }
    }

    private void buildQuery(JsonNode jsonNode, String path, Map<String, Collection<String>> queries) {
        if (!jsonNode.isContainerNode()) { // 叶子节点
            if (jsonNode.isNull()) {
                return;
            }
            Collection<String> values = queries.computeIfAbsent(path, k -> new ArrayList<>());
            values.add(jsonNode.asText());
            return;
        }
        if (jsonNode.isArray()) { // 数组节点
            Iterator<JsonNode> it = jsonNode.elements();
            while (it.hasNext()) {
                buildQuery(it.next(), path, queries);
            }
        } else {
            Iterator<Map.Entry<String, JsonNode>> it = jsonNode.fields();
            while (it.hasNext()) {
                Map.Entry<String, JsonNode> entry = it.next();
                if (StringUtils.hasText(path)) {
                    buildQuery(entry.getValue(), path + "." + entry.getKey(), queries);
                } else { // 根节点
                    buildQuery(entry.getValue(), entry.getKey(), queries);
                }
            }
        }
    }
}
