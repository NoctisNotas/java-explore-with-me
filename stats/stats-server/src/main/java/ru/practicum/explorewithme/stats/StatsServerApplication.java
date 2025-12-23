package ru.practicum.explorewithme.stats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"ru.practicum.ewm"})
public class StatsServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(StatsServerApplication.class, args);
    }
}
