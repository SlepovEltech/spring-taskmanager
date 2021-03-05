package com.example.springbootbackend.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "completed")
    private boolean completed;

    @Column(name = "deadline")
    private Date deadline;

    public Task() {

    }

    public Task(String title, String description, boolean completed, Date deadline) {
        this.title = title;
        this.completed = completed;
        this.description = description;
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "Task [id=" + id + ", title=" + title + ", desc=" + description + ", published=" + completed + "]";
    }
}