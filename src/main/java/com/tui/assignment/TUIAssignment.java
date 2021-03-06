package com.tui.assignment;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class TUIAssignment {

    @Bean
    public RestTemplate createRestClientBean() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(TUIAssignment.class, args);
    }

}
