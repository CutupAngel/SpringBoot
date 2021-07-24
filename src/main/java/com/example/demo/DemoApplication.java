package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {

        System.out.println("DemoApplication is not started yet");
        SpringApplication.run(DemoApplication.class, args);

        System.out.println("DemoApplication is already started");
    }
}
