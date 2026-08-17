package com.dionisius.taskmanager.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "app")
@Data
public class AppProperties {

    @NotBlank
    private String name;
    @NotBlank
    private String version;
    @Min(1)
    @Max(20)
    private int maxTasksPerPage;
}
