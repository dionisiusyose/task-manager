package com.dionisius.taskmanager.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dionisius.taskmanager.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{
    List<Task> findByCompleted(boolean completed);
    List<Task> findByTitleContainingIgnoreCase(String title);
    @Query("SELECT t FROM Task t WHERE t.completed =:completed")
    List<Task> findTasksByCompletionStatus(@Param("completed") boolean completed);

    Page<Task> findByCompleted(boolean completed, Pageable pageable);
    Page<Task> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    @Query("SELECT t FROM Task t WHERE t.completed =:completed")
    Page<Task> findTasksByCompletionStatus(@Param("completed") boolean completed, Pageable pageable);
    @Query("SELECT t FROM Task t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%')) AND t.completed = :completed")
    Page<Task> findByTitleContainingAndCompleted(String title, boolean completed, Pageable pageable);
}
