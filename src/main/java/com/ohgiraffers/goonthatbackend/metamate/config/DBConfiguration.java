package com.ohgiraffers.goonthatbackend.metamate.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.Properties;

public class DBConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.jpa")
    public Properties hibernateConfig(){
        return new Properties();
    }
}
