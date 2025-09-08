package com.example.todo.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class CreateTodoRequest {
    @NotBlank(message = "Description cannot be empty")
    private String description;

    @FutureOrPresent(message = "Due date must be in the present or future")
    private LocalDateTime dueDatetime;

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getDueDatetime() { return dueDatetime; }
    public void setDueDatetime(LocalDateTime dueDatetime) { this.dueDatetime = dueDatetime; }
}
