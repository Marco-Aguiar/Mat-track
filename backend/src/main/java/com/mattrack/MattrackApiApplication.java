package com.mattrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class MattrackApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(MattrackApiApplication.class, args);
    }
}
