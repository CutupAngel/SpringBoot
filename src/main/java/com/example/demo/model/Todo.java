package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@Builder
public class Todo {
    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    Long id;
    @Column
    String ipAddress;
    @Column
    String hostName;
    @Column
    TodoStatus todoStatus;

    public Todo(String ip, String host, TodoStatus todoStatus) {
        this.ipAddress = ip;
        this.hostName = host;
        this.todoStatus = todoStatus;
    }

    public Todo() {
    }
}
