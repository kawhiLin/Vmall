package com.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication(exclude= DispatcherServletAutoConfiguration.class)

public class ProductVmallApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ProductVmallApplication.class, args);
    }
}
