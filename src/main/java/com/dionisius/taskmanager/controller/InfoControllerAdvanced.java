package com.dionisius.taskmanager.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dionisius.taskmanager.config.AppProperties;

@RestController
@RequestMapping("/api/v1/info/advanced")
public class InfoControllerAdvanced {
    private final AppProperties appProperties;

    InfoControllerAdvanced(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @GetMapping
    public Map<String, Object> getInfo(){
        Map<String, Object> map = new HashMap<>();
        map.put("appName", appProperties.getName());
        map.put("version", appProperties.getVersion());
        map.put("maxTasksPerPage", appProperties.getMaxTasksPerPage());

        return map;
    }
}
