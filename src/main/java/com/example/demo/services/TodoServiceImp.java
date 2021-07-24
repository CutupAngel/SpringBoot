package com.example.demo.services;

import com.example.demo.model.Todo;
import com.example.demo.model.TodoStatus;
import com.example.demo.repositories.TodoRepository;
import com.itextpdf.text.DocumentException;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TodoServiceImp implements TodoService{
    TodoRepository todoRepository;

    public TodoServiceImp(TodoRepository todoRepository){
        this.todoRepository = todoRepository;
    }

    @Override
    public List<Todo> getTodos() {
        List<Todo> todos = new ArrayList<>();
        todoRepository.findAll().forEach(todos::add);
        return todos;
    }

    @Override
    public Todo getTodoById(Long id) {
        return todoRepository.findById(id).get();
    }

    @Override
    public Todo getTodoByIPAddress(String IPAddress) {
        return todoRepository.findByipAddress(IPAddress);
    }

    @Override
    public List<Todo> getTodoByTodoStatus(TodoStatus status) {
        return todoRepository.findBytodoStatus(status);
    }

    @Override
    public Todo getTodoByHostName(String hostName) {
        return todoRepository.findByhostName(hostName);
    }

    @Override
    public Todo insert(Todo todo) {
        return todoRepository.save(todo);
    }

    @Override
    public void updateTodo(Long id, Todo todo) {
        Todo todoFromDb = todoRepository.findById(id).get();
        System.out.println(todoFromDb.toString());
        todoFromDb.setTodoStatus(todo.getTodoStatus());
        todoFromDb.setIpAddress(todo.getIpAddress());
        todoFromDb.setHostName(todo.getHostName());
        todoRepository.save(todoFromDb);
    }

    @Override
    public void deleteTodo(Long todoId) {
        todoRepository.deleteById(todoId);
    }

    @Override
    public ByteArrayOutputStream generateVoucherDocumentBaos(String html) throws IOException, DocumentException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(baos);
        baos.close();

        return baos;

    }

    @Override
    public String buildHtmlFromTemplate(String voucherUUid, Date created, String employerDenomination, String employerEmail) {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCacheable(false);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("voucherUuid", voucherUUid);
        context.setVariable("voucherCreated", created);
        context.setVariable("employerDenomination", employerDenomination);
        context.setVariable("employerEmail", employerEmail);

        // Get the plain HTML with the resolved ${name} variable!
        return templateEngine.process("pdf_templates/voucher", context);

    }

}
