package com.example.todo.controller;

import com.example.todo.dto.CreateTodoRequest;
import com.example.todo.dto.UpdateDescriptionRequest;
import com.example.todo.model.TodoItem;
import com.example.todo.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)  // ✅ 201 Created
    public TodoItem create(@Valid @RequestBody CreateTodoRequest request) {
        TodoItem item = new TodoItem();
        item.setDescription(request.getDescription());
        item.setDueDatetime(request.getDueDatetime());
        return service.add(item);
    }

    @GetMapping("/{id}")
    public TodoItem get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping
    public List<TodoItem> getAll(@RequestParam(required = false) Boolean onlyNotDone) {
        return service.getAll(Boolean.TRUE.equals(onlyNotDone));
    }

    @PutMapping("/{id}/description")
    public TodoItem updateDescription(@PathVariable Long id, @Valid @RequestBody UpdateDescriptionRequest request) {
        return service.updateDescription(id, request.getDescription());
    }

    @PutMapping("/{id}/done")
    public TodoItem markDone(@PathVariable Long id) {
        return service.markDone(id);
    }

    @PutMapping("/{id}/not-done")
    public TodoItem markNotDone(@PathVariable Long id) {
        return service.markNotDone(id);
    }
}
