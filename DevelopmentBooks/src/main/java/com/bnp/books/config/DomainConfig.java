package com.bnp.books.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bnp.books.domain.BookPriceCalculator;

@Configuration
public class DomainConfig {

    @Bean
    BookPriceCalculator bookPriceCalculator() {
        return new BookPriceCalculator();
    }
}

