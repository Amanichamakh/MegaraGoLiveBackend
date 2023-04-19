package com.example.megaragolive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:application.properties")
public class EntityManager {

    @Autowired
    Environment env;

    @Bean
    public String getExtractionPath(){
        return env.getProperty("extractPath") ;
    }
}