package com.web;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;

@SpringBootApplication(exclude= DispatcherServletAutoConfiguration.class)

public class WebVmallApplication {

    public static void main(String[] args) {

        SpringApplication.run(WebVmallApplication.class, args);
    }

}
