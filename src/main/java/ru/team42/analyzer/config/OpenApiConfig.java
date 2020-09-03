package ru.team42.analyzer.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.function.Predicate;

import static java.util.function.Predicate.not;
import static springfox.documentation.builders.PathSelectors.regex;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;
import static springfox.documentation.builders.RequestHandlerSelectors.withClassAnnotation;

/**
 * Конфигурация для Swagger
 */
@Configuration
@EnableSwagger2
public class OpenApiConfig {

    @Value("${spring.application.name:#{null}}")
    private String appName;
    @Value("${application.description:#{null}}")
    private String appDescription;
    @Value("${application.version:#{null}}")
    private String appVersion;

    @Value("${swagger.contact.name:#{null}}")
    private String contactName;
    @Value("${swagger.contact.url:#{null}}")
    private String contactUrl;

    @Value("${swagger.externalDoc.description:#{null}}")
    private String externalDocDescription;

    @Value("${swagger.externalDoc.description.url:#{null}}")
    private String externalDocUrl;


    @Bean
    public Docket swaggerInternalApiConfiguration() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("internal-api")
                .apiInfo(buildInternalApiInfo())
                .select()
                .apis(not(
                        withClassAnnotation(ApiIgnore.class))
                        .and(
                                basePackage("ru.team42.analyzer.controllers.internal")
                        )).paths(not(paths()))
                .build();

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

    private ApiInfo buildInternalApiInfo() {
        return  new ApiInfo(
                "Internal Api Documentation",
                "Internal Api Documentation",
                appVersion,
                "urn:tos",
                new Contact(contactName, contactUrl, ""),
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
                new Contact(contactName, contactUrl, ""),
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>());
    }



    //                        .apiInfo(apiInfo())
//                        .securitySchemes(securitySchemes())
//                        .securityContext(securityContext());

    private Predicate<String> paths() {
        return regex("/api.*");
    }

}