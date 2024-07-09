package ru.task5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(scanBasePackages = "ru.task5")
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class);
    }
}
