package com.buddy.profile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.buddy.client.auth")
public class BuddyProfileApplication {
    public static void main(String[] args) {
        SpringApplication.run(BuddyProfileApplication.class, args);
    }
}