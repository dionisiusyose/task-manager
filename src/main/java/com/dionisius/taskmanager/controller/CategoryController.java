package com.dionisius.taskmanager.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dionisius.taskmanager.entity.Category;
import com.dionisius.taskmanager.service.CategoryService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }
    @GetMapping
    public Category findById(@PathVariable Long id) {
        return categoryService.findById(id);
    }
    
    @PostMapping
    public ResponseEntity<Category> create(@RequestBody Category category) {
        Category create = categoryService.create(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(create);
    }
    
}
