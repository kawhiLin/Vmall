package com.shoppingcar;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(exclude= DispatcherServletAutoConfiguration.class)
public class ShoppingcarVmallApplication {

    public static void main(String[] args) throws Exception {
//        Log4jUtils.init();
        SpringApplication.run(ShoppingcarVmallApplication.class, args);
    }

}
