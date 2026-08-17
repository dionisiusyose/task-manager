package com.dionisius.taskmanager.dto;

import lombok.Builder;

@Builder
public record CategoryResponse(
    Long categoryId,
    String name,
    String description
) 
    {
} 