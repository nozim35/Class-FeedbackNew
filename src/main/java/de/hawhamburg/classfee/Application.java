package de.hawhamburg.classfee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);

    }
}