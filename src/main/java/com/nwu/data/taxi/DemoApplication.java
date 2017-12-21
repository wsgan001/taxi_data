package com.nwu.data.taxi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@ComponentScan
public class DemoApplication {
    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("PST"));
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}