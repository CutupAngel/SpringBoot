package com.example.demo.repositories;

import com.example.demo.model.Todo;
import com.example.demo.model.TodoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface TodoRepository extends JpaRepository<Todo, Long> {
    Todo findByipAddress(String ipAddress);
    Todo findByhostName(String hostName);
    List<Todo> findBytodoStatus(TodoStatus status);
}
