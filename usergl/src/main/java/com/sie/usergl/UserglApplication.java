package com.sie.usergl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

@MapperScan("com.sie.usergl.mapper")
public class UserglApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserglApplication.class, args);

    }

}
