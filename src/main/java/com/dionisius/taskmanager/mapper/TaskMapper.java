package com.dionisius.taskmanager.mapper;

import org.springframework.stereotype.Component;

import com.dionisius.taskmanager.dto.CategoryResponse;
import com.dionisius.taskmanager.dto.TaskRequest;
import com.dionisius.taskmanager.dto.TaskResponse;
import com.dionisius.taskmanager.entity.Category;
import com.dionisius.taskmanager.entity.Task;
import com.dionisius.taskmanager.service.CategoryService;

@Component
public class TaskMapper {

    private final CategoryService categoryService;

    public TaskMapper(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    public Task toEntity(TaskRequest request){

        Category category = null;

        if(request != null && request.categoryId() != null){
            category = categoryService.findById(request.categoryId());
        }

        return Task.builder()
            .title(request.title())
            .description(request.description())
            .completed(request.completed() != null ? request.completed() : false)
            .category(category)
            .build();
    }

    public TaskResponse toResponse(Task task){
        CategoryResponse categoryResponse = null;

        if(task!=null && task.getCategory() !=null){
            categoryResponse = CategoryResponse.builder()
                .categoryId(task.getCategory().getId())
                .name(task.getCategory().getName())
                .description(task.getCategory().getDescription())
                .build();
        }

        return TaskResponse.builder()
            .id(task.getId())
            .title(task.getTitle())
            .description(task.getTitle())
            .completed(task.getCompleted())
            .createdAt(task.getCreatedAt())
            .category(categoryResponse)
            .build();
    }

    public void updateEntityFromRequest(Task task, TaskRequest request){
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setCompleted(request.completed());

        if(request.categoryId() != null){
            task.setCategory(categoryService.findById(request.categoryId()));
        }
    }
}
