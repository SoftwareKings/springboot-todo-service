package com.example.todo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonValue;

@Entity
public class TodoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime creationDatetime;
    private LocalDateTime dueDatetime;
    private LocalDateTime doneDatetime;

    public enum Status {
        NOT_DONE("not done"),
        DONE("done"),
        PAST_DUE("past due");

        private final String value;

        Status(String value) { 
            this.value = value; 
        }

        @JsonValue
        public String getValue() { 
            return value; 
        }
    }

    @PrePersist
    public void prePersist() {
        this.creationDatetime = LocalDateTime.now();
        this.status = Status.NOT_DONE;
    }

    public Long getId() { return id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public LocalDateTime getCreationDatetime() { return creationDatetime; }
    public void setCreationDatetime(LocalDateTime creationDatetime) { this.creationDatetime = creationDatetime; }

    public LocalDateTime getDueDatetime() { return dueDatetime; }
    public void setDueDatetime(LocalDateTime dueDatetime) { this.dueDatetime = dueDatetime; }

    public LocalDateTime getDoneDatetime() { return doneDatetime; }
    public void setDoneDatetime(LocalDateTime doneDatetime) { this.doneDatetime = doneDatetime; }
}
