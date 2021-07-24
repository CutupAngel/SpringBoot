package com.example.demo.bootstrap;

import com.example.demo.model.Todo;
import com.example.demo.model.TodoStatus;
import com.example.demo.repositories.TodoRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

@Component
public class TodoLoader implements CommandLineRunner {

    public final TodoRepository todoRepository;

//    @Autowired
//    private JavaMailSender javaMailSender;

    public TodoLoader(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public void run(String... args) {
        loadTodos();
    }

    private void checkHosts() throws UnknownHostException {
        System.out.println("CommandLineRunner is my ip address");
        StringBuilder hostName = new StringBuilder(InetAddress.getLocalHost().toString());
        int index = hostName.indexOf("/");
        int dotIndex = 0, dotPIndex;
        System.out.println(InetAddress.getLocalHost().toString());
        do {
            dotPIndex = dotIndex;
            dotIndex = hostName.indexOf(".", dotPIndex+1);
        }while ( dotIndex != -1 ) ;
        String ipAddress = hostName.substring(index+1, dotPIndex);
        System.out.println(ipAddress + " is my ip address");
        int timeout=1000;
        for (int i=1;i<255;i++){
            String host=ipAddress + "." + i;
            try {
                if (InetAddress.getByName(host).isReachable(timeout)){
                    Todo todo = new Todo(host, getHostFromIp(host), TodoStatus.AVAILABLE);
                    System.out.println(host + " is reachable");
                    todoRepository.save(todo);
                } else {
                    Todo todo = new Todo(host, "Unnamed(due to con)", TodoStatus.UNAVAILABLE);
                    todoRepository.save(todo);
                    sendEmail(host);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getHostFromIp(String ipaddr) throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getByName(ipaddr);
        return  inetAddress.getHostName();
    }

    private void loadTodos() {
        if (todoRepository.count() == 0) {
            try {
                checkHosts();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            System.out.println("Sample Todos Loaded");
        } else {
            List<Todo> todoList = todoRepository.findBytodoStatus(TodoStatus.UNAVAILABLE);
            for (Todo todo :
                    todoList) {
                sendEmail(todo.getIpAddress());
            }
        }
    }

    @SneakyThrows
    public void sendEmail(String msgTxt) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("jupiter1206jove@gmail.com", "angel126mic@gmail.com");

        msg.setSubject("Testing from Spring Boot");
        msg.setText(msgTxt);

//        javaMailSender.send(msg);
    }
}
