package com.buddy.post;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.buddy.post.config.MinioProperties;

@EnableConfigurationProperties(MinioProperties.class)
@SpringBootApplication
public class BuddyPostApplication {
    public static void main(String[] args) {
        SpringApplication.run(BuddyPostApplication.class, args);
    }
}