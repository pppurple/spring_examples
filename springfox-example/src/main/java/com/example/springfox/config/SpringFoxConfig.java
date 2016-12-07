package com.example.springfox.config;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Date;
import java.util.List;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;
import static springfox.documentation.schema.AlternateTypeRules.newRule;

import com.example.springfox.controller.CountryController.MyResponseEntity;
import com.example.springfox.controller.CountryController.MyResponseEntityWithStatus;
import com.example.springfox.controller.CountryController.Country;

@Configuration
//@EnableSwagger2
public class SpringFoxConfig {
    @Autowired
    private TypeResolver typeResolver;

//    @Bean
    public Docket springFoxExampleDoc() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                    .apis(RequestHandlerSelectors.any())
                    .paths(paths())
                    .build()
                .pathMapping("/")
                .directModelSubstitute(Date.class, String.class)
                .genericModelSubstitutes(MyResponseEntityWithStatus.class)
                .alternateTypeRules(
                        newRule(typeResolver.resolve(MyResponseEntity.class,
                                    typeResolver.resolve(List.class, Country.class)),
                                typeResolver.resolve(Country.class))
                )
                .useDefaultResponseMessages(false)
                .enableUrlTemplating(true)
                .apiInfo(apiInfo());
    }

//    @Bean
    UiConfiguration uiConfiguration() {
        return new UiConfiguration(
                "",
                UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS
        );
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("My SpringFox Example Api")
                .description("My SpringFox description xxxxxx.")
                .version("1.0")
                .license("Apache License v2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .build();
    }

    private Predicate<String> paths() {
        return or(
                regex("/api/people.*"),
                regex("/api/country.*")
        );
    }
}
