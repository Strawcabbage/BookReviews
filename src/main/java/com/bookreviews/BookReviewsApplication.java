package com.bookreviews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BookReviewsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookReviewsApplication.class, args);
    }

}
