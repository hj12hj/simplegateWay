package com.example.demo;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@RestController
public class GatewayController {

    private final RestTemplate restTemplate;

    public GatewayController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping(value = "/**", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    public Object forwardRequest(@RequestParam MultiValueMap<String, String> params, @RequestBody(required = false) Object body, @RequestHeader HttpHeaders headers, HttpMethod method, HttpServletRequest request) {
        // 构建目标URL
        String targetUrl = "http://10.7.4.10:9000" + request.getRequestURI() + "?" + request.getQueryString();

        // 转发请求
        ResponseEntity<Object> response = restTemplate.exchange(targetUrl, method, new HttpEntity<>(body, headers), Object.class);

        // 返回转发响应
        return response.getBody();
    }
}
