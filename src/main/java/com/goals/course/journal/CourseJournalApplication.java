package com.goals.course.journal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CourseJournalApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseJournalApplication.class, args);
    }

}
