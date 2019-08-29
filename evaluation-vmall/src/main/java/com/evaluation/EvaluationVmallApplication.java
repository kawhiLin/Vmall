package com.evaluation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(exclude= DispatcherServletAutoConfiguration.class)
@RestController
public class EvaluationVmallApplication {

    public static void main(String[] args) {
//        Log4jUtils.init();
        SpringApplication.run(EvaluationVmallApplication.class, args);
    }

}
