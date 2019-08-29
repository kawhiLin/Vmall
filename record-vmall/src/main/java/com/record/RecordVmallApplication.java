package com.record;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;



@SpringBootApplication(exclude= DispatcherServletAutoConfiguration.class)

public class RecordVmallApplication {

    public static void main(String[] args) {
//        Log4jUtils.init();
        SpringApplication.run(RecordVmallApplication.class, args);
    }

}
