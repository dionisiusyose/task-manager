package com.dionisius.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dionisius.taskmanager.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
