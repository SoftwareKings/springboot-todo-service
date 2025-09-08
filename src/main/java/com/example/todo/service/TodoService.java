package com.example.todo.service;

import com.example.todo.model.TodoItem;
import com.example.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TodoService {
    private final TodoRepository repository;

    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public TodoItem add(TodoItem item) {
        return repository.save(item);
    }

    public TodoItem get(Long id) {
        return repository.findById(id)
                .map(this::updatePastDue)
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Item not found"
                ));
    }

    public List<TodoItem> getAll(boolean onlyNotDone) {
        if (onlyNotDone) {
            return repository.findByStatus(TodoItem.Status.NOT_DONE)
                    .stream().map(this::updatePastDue).toList();
        }
        return repository.findAll().stream().map(this::updatePastDue).toList();
    }

    public TodoItem updateDescription(Long id, String description) {
        TodoItem item = get(id);
        checkImmutable(item);
        item.setDescription(description);
        return repository.save(item);
    }

    public TodoItem markDone(Long id) {
        TodoItem item = get(id);
        checkImmutable(item);
        item.setStatus(TodoItem.Status.DONE);
        item.setDoneDatetime(LocalDateTime.now());
        return repository.save(item);
    }

    public TodoItem markNotDone(Long id) {
        TodoItem item = get(id);
        checkImmutable(item);
        item.setStatus(TodoItem.Status.NOT_DONE);
        item.setDoneDatetime(null);
        return repository.save(item);
    }

    private TodoItem updatePastDue(TodoItem item) {
        if (item.getStatus() == TodoItem.Status.NOT_DONE &&
                item.getDueDatetime() != null &&
                LocalDateTime.now().isAfter(item.getDueDatetime())) {
            item.setStatus(TodoItem.Status.PAST_DUE);
            repository.save(item);
        }
        return item;
    }

    private void checkImmutable(TodoItem item) {
        if (item.getStatus() == TodoItem.Status.PAST_DUE) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Past due items cannot be modified"
            );
        }
    }
}
