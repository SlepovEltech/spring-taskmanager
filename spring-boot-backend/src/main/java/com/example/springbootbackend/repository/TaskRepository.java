package com.example.springbootbackend.repository;

import com.example.springbootbackend.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {

    List<Task> findAllByCompleted(boolean completed);
    List<Task> findAllByTitleContaining(String title);
     
}
