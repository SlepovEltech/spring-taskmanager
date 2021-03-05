package com.example.springbootbackend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbootbackend.model.Task;
import com.example.springbootbackend.repository.TaskRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class TaskController {
    @Autowired
    TaskRepository taskRepository;

    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getAllTutorials(@RequestParam(required = false) String title) {
        try {
            List<Task> tasks = new ArrayList<Task>();

            if (title == null)
                taskRepository.findAll().forEach(tasks::add);
            else
                taskRepository.findAllByTitleContaining(title).forEach(tasks::add);
            for (Task task: tasks) {
                System.out.println(task);
            }
            if (tasks.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }


            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<Task> updateTutorial(@PathVariable("id") long id, @RequestBody Task newTask) {
        Optional<Task> taskData = taskRepository.findById(id);

        if (taskData.isPresent()) {
            Task _task = taskData.get();
            _task.setTitle(newTask.getTitle());
            _task.setDescription(newTask.getDescription());
            _task.setCompleted(newTask.isCompleted());
            _task.setDeadline(newTask.getDeadline());
            return new ResponseEntity<>(taskRepository.save(_task), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/tasks")
    public ResponseEntity<Task> createTutorial(@RequestBody Task newTask) {
        try {
            Task _task = taskRepository
                    .save(new Task(newTask.getTitle(), newTask.getDescription(),
                            newTask.isCompleted(), newTask.getDeadline()));
            return new ResponseEntity<>(newTask, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
