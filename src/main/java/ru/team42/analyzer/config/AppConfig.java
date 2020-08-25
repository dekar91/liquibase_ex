package ru.team42.analyzer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.function.Predicate;

import static java.util.function.Predicate.not;
import static springfox.documentation.builders.PathSelectors.regex;
import static springfox.documentation.builders.RequestHandlerSelectors.withClassAnnotation;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

@Configuration
public class AppConfig {
    @Bean
    public Docket swaggerInternalApiConfiguration() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("internal-api")
                .apiInfo(buildInternalApiInfo())
                    .select()
                    .apis(not(
                            withClassAnnotation(ApiIgnore.class))
                            .and(
                                    basePackage("ru.team42.analyzer.controllers")
                            )).paths(not(paths()))
                    .build();

    }

    private ApiInfo buildInternalApiInfo() {
        return  new ApiInfo(
                "Api Documentation",
                "Internal Api Documentation",
                "1.0",
                "urn:tos",
                new Contact("Denis", "http://test.ru", ""),
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>());
    }

    private ApiInfo buildCounterApiInfo() {
        return  new ApiInfo(
                "Api Documentation",
                "Counter Api Documentation",
                "1.0",
                "urn:tos",
                new Contact("Denis", "http://test.ru", ""),
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>());
    }

    @Bean
    public Docket swaggerCounterApiConfiguration() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("counter-api")
                .apiInfo(buildCounterApiInfo())
                .select()
                .apis(not(withClassAnnotation(ApiIgnore.class)))
                .paths(paths())
                .build();

    }

    //                        .apiInfo(apiInfo())
//                        .securitySchemes(securitySchemes())
//                        .securityContext(securityContext());

    private Predicate<String> paths() {
        return regex("/api.*");
    }

}
