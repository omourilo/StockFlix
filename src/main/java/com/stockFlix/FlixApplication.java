package com.stockFlix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class FlixApplication {

	public static void main(String[] args) {

        // Carrega o .env ANTES de qualquer coisa do Spring
        Dotenv dotenv = Dotenv.load();
        dotenv.entries().forEach(e ->
            System.setProperty(e.getKey(), e.getValue())
        );

        SpringApplication.run(FlixApplication.class, args);
    }
}
