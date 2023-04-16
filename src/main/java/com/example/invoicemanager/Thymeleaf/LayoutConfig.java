package com.example.invoicemanager.Thymeleaf;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Configuration
public class LayoutConfig {

    /*public SpringTemplateEngine thymeleafLayoutDialect(){
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.addDialect(layoutDialect());
        return engine;
    }*/

    @Bean
    public LayoutDialect layoutDialect(){
        return new LayoutDialect();
    }
}
