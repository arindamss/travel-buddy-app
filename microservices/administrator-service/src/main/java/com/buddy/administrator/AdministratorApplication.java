package com.buddy.administrator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.buddy.client.auth")
public class AdministratorApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdministratorApplication.class, args);
    }
}