package com.example.todo;

import com.example.todo.model.TodoItem;
import com.example.todo.repository.TodoRepository;
import com.example.todo.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TodoServiceTest {

    @Autowired
    private TodoRepository repository;

    @Autowired
    private TodoService service;

    @BeforeEach
    void clearDb() {
        repository.deleteAll();
    }

    @Test
    void overdueItemBecomesPastDueOnFetch() {
        TodoItem item = new TodoItem();
        item.setDescription("Overdue task");
        item.setDueDatetime(LocalDateTime.now().minusDays(1));
        repository.save(item);

        TodoItem fetched = service.get(item.getId());
        assertEquals(TodoItem.Status.PAST_DUE, fetched.getStatus());
    }

    @Test
    void cannotModifyPastDueItem() {
        TodoItem item = new TodoItem();
        item.setDescription("Locked task");
        item.setDueDatetime(LocalDateTime.now().minusDays(1));
        repository.save(item);

        TodoItem fetched = service.get(item.getId());
        assertThrows(ResponseStatusException.class, () ->
                service.updateDescription(fetched.getId(), "new description")
        );
    }

    @Test
    void markDoneSetsDoneDatetime() {
        TodoItem item = new TodoItem();
        item.setDescription("Finish test");
        item.setDueDatetime(LocalDateTime.now().plusDays(1));
        repository.save(item);

        TodoItem updated = service.markDone(item.getId());
        assertEquals(TodoItem.Status.DONE, updated.getStatus());
        assertNotNull(updated.getDoneDatetime());
    }
}
