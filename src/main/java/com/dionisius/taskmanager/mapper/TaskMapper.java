package com.dionisius.taskmanager.mapper;

import org.springframework.stereotype.Component;

import com.dionisius.taskmanager.dto.TaskRequest;
import com.dionisius.taskmanager.dto.TaskResponse;
import com.dionisius.taskmanager.entity.Task;

@Component
public class TaskMapper {
    public Task toEntity(TaskRequest request){
        return Task.builder()
            .title(request.title())
            .description(request.description())
            .completed(request.completed() != null ? request.completed() : false)
            .build();
    }

    public TaskResponse toResponse(Task task){
        return TaskResponse.builder()
            .id(task.getId())
            .title(task.getTitle())
            .description(task.getTitle())
            .completed(task.getCompleted())
            .createdAt(task.getCreatedAt())
            .build();
    }

    public void updateEntityFromRequest(Task task, TaskRequest request){
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setCompleted(request.completed());
    }
}
