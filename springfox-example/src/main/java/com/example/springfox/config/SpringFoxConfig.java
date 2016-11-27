package com.example.springfox.config;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {
    @Bean
    public Docket springFoxExampleDoc() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                    .apis(RequestHandlerSelectors.any())
                    .paths(PathSelectors.any())
                    .build()
                .pathMapping("/")
                .apiInfo(apiInfo());
    }

    @Bean
    UiConfiguration uiConfiguration() {
        return new UiConfiguration(
                "",
                UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS
        );
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("My SpringFox Example Api").version("1.0").build();
    }

    private Predicate<String> paths() {
        return or(
                regex("")
        );
    }
}
