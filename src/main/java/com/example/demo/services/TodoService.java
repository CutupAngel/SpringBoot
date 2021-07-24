package com.example.demo.services;

import com.example.demo.model.Todo;
import com.example.demo.model.TodoStatus;
import com.itextpdf.text.DocumentException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface TodoService {
    List<Todo> getTodos();

    Todo getTodoById(Long id);

    Todo getTodoByIPAddress(String IPAddress);

    List<Todo> getTodoByTodoStatus(TodoStatus status);

    Todo getTodoByHostName(String hostName);

    Todo insert(Todo todo);

    void updateTodo(Long id, Todo todo);

    void deleteTodo(Long todoId);

    ByteArrayOutputStream generateVoucherDocumentBaos(String html) throws IOException, DocumentException;

    String buildHtmlFromTemplate(String voucherUUid, Date created, String employerDenomination, String employerEmail);

}
