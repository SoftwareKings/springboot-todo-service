package com.example.todo.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdateDescriptionRequest {
    @NotBlank(message = "Description cannot be empty")
    private String description;

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
