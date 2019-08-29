package com.user;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories
@SpringBootApplication(exclude= DispatcherServletAutoConfiguration.class)

public class UserApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(UserApplication.class, args);
    }

}
