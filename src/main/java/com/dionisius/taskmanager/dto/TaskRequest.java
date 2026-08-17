package com.dionisius.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaskRequest(
    @NotBlank(message = "Title is mandatory")
    @Size(min = 3, max = 100)
    String title, 
    @Size(max = 500, message = "Description can be up to 500 characters")
    String description,
    Boolean completed
) {

}
