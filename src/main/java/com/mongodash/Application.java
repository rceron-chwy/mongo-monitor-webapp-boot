package com.mongodash;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.mongodash.spring")
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {
    	
    	SpringApplication app = new SpringApplication(Application.class);
    	ApplicationContext ctx = app.run(args);
    	
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }
}
