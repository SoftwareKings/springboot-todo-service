package com.example.todo.repository;

import com.example.todo.model.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TodoRepository extends JpaRepository<TodoItem, Long> {
    List<TodoItem> findByStatus(TodoItem.Status status);
}
