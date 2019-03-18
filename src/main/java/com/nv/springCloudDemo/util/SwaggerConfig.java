package com.nv.springCloudDemo.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    // Swagger v2 -> /v2/api-docs
    // Swagger ui -> /swagger-ui.html
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2);
    }
}
