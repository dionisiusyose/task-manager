package com.dionisius.taskmanager.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dionisius.taskmanager.entity.Task;
import com.dionisius.taskmanager.service.TaskService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    // Constructor Injection
    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }


    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        Task savedTask = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }
    
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id,
                           @Valid @RequestBody Task updatedTask) {
        return taskService.updateTask(id, updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/completed/{status}")
    public List<Task> getTaskByCompletions(@PathVariable boolean status) {
        return taskService.getTasksByCompletionStatus(status);
    }
    
    @GetMapping("/search")
    public  List<Task>  searchTasksByTitle(@RequestParam String title) {
        return taskService.searchTasksByTitle(title);
    }
    
}
