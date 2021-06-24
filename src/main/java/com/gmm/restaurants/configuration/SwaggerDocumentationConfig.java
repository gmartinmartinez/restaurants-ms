package com.gmm.restaurants.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerDocumentationConfig {

    @Bean
    public Docket customImplementation(){
        return new Docket(DocumentationType.OAS_30)
                .select()
                    .apis(RequestHandlerSelectors.basePackage("com.gmm.restaurants.controllers"))
                    .build()
                .apiInfo(apiInfo());
    }

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Restaurants API")
            .description("proyecto final api evangelist - Gema Martin")
            .license("")
            .licenseUrl("http://unlicense.org")
            .termsOfServiceUrl("")
            .version("v1")
            .contact(new Contact("","", "gema.m2@gmail.com"))
            .build();
    }

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
            .info(new Info()
                .title("Restaurants API")
                .description("proyecto final api evangelist ")
                .termsOfService("")
                .version("v1")
                .license(new License()
                    .name("")
                    .url("http://unlicense.org"))
                .contact(new io.swagger.v3.oas.models.info.Contact()
                    .email("gema.m2@gmail.com")));
    }

}
