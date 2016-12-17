package com.example.springfox.config;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import com.example.springfox.controller.CountryController.MyResponseEntity;
import com.example.springfox.controller.CountryController.Country;

import java.util.List;

import static springfox.documentation.schema.AlternateTypeRules.newRule;

@Configuration
//@EnableSwagger2
public class SpringFoxConfigAlternateTypeRules {
    @Autowired
    private TypeResolver typeResolver;

//    @Bean
    public Docket springFoxExampleDoc() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                    .apis(RequestHandlerSelectors.any())
                    .paths(PathSelectors.any())
                    .build()
                .alternateTypeRules(
                        newRule(typeResolver.resolve(MyResponseEntity.class,
                                    typeResolver.resolve(List.class, Country.class)),
                                typeResolver.resolve(Country.class))
                );
    }

}
