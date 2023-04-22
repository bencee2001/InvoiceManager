package com.example.invoicemanager.Thymeleaf;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Configuration
public class LayoutConfig {
    @Bean
    public LayoutDialect layoutDialect(){
        return new LayoutDialect();
    }
}
