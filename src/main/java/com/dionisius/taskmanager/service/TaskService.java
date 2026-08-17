package com.dionisius.taskmanager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dionisius.taskmanager.entity.Task;
import com.dionisius.taskmanager.exception.TaskNotFoundException;
import com.dionisius.taskmanager.repository.TaskRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id){
        return taskRepository.findById(id)
            .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task createTask(Task task){
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task updatedTask){
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setCompleted(updatedTask.getCompleted());
        return taskRepository.save(task);
    }

    public void deleteTask(Long id){
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new TaskNotFoundException(id));
        taskRepository.delete(task);
    }

    public List<Task> getTasksByCompletionStatus(boolean status){
        return taskRepository.findByCompleted(status);
    }

    public List<Task> searchTasksByTitle(String title){
        return taskRepository.findByTitleContainingIgnoreCase(title);
    }

}
