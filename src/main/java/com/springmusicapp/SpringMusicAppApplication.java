package com.springmusicapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringMusicAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMusicAppApplication.class, args);
    }

    @GetMapping
    public String hello() {
        return "Hello Spring Music App";
    }
}
