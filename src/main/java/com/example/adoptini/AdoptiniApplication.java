package com.example.adoptini;

import com.example.adoptini.property.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({FileStorageProperties.class})
public class AdoptiniApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdoptiniApplication.class, args);
    }

}
