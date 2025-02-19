package com.example.demo.service;

import com.example.demo.model.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaskService {

    private final List<Task> tasks = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1); // Generate unique IDs

    public List<Task> getAllTasks() {
        return tasks;
    }

    public Task addTask(String description) {
        Task task = new Task(description);
        task.setId(idGenerator.getAndIncrement());
        tasks.add(task);
        return task;
    }

    public void markCompleted(Long taskId) {
        tasks.stream()
                .filter(task -> task.getId().equals(taskId))
                .findFirst()
                .ifPresent(task -> task.setCompleted(true));
    }

    //Optional:  Delete Task
    public void deleteTask(Long taskId) {
        tasks.removeIf(task -> task.getId().equals(taskId));
    }
}