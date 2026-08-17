package com.dionisius.taskmanager.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dionisius.taskmanager.dto.TaskRequest;
import com.dionisius.taskmanager.dto.TaskResponse;
import com.dionisius.taskmanager.entity.Task;
import com.dionisius.taskmanager.exception.TaskNotFoundException;
import com.dionisius.taskmanager.mapper.TaskMapper;
import com.dionisius.taskmanager.repository.TaskRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper){
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public List<TaskResponse> getAllTasks(){
        final List<Task> completedTasks = taskRepository.findAll();
        return completedTasks.stream()
            .map(taskMapper::toResponse)
            .toList();
    }

    public Page<TaskResponse> getAllTasks(Pageable pageable){
        Page<Task> retrievedTasks = taskRepository.findAll(pageable);
        return retrievedTasks.map(taskMapper::toResponse);
    }

    public TaskResponse getTaskById(Long id){
        Task retrievedTask = taskRepository.findById(id)
            .orElseThrow(() -> new TaskNotFoundException(id));

        return taskMapper.toResponse(retrievedTask);
    }

    public TaskResponse createTask(TaskRequest task){
        Task entityTask = taskMapper.toEntity(task);
        Task savedTask = taskRepository.save(entityTask);    
        return taskMapper.toResponse(savedTask);
    }

    public TaskResponse updateTask(Long id, TaskRequest updatedTask){
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        taskMapper.updateEntityFromRequest(task, updatedTask);
        taskRepository.save(task);
        return taskMapper.toResponse(taskRepository.save(task));
    }

    public void deleteTask(Long id){
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new TaskNotFoundException(id));
        taskRepository.delete(task);
    }

    public List<TaskResponse> getTasksByCompletionStatus(boolean status){
        final List<Task> completedTasks = taskRepository.findByCompleted(status);
        return completedTasks.stream()
            .map(taskMapper::toResponse)
            .toList();
    }

    public Page<TaskResponse> getTasksByCompletionStatus(boolean status, Pageable pageable){
        final Page<Task> completedTasks = taskRepository.findByCompleted(status, pageable);
        return completedTasks.map(taskMapper::toResponse);
    }

    public List<TaskResponse> searchTasksByTitle(String title){
        final List<Task> completedTasks = taskRepository.findByTitleContainingIgnoreCase(title);
        return completedTasks.stream()
            .map(taskMapper::toResponse)
            .toList();
    }

}
