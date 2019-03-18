package com.nv.springCloudDemo.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    public static final Contact DEFAULT_CONTACT = new Contact("Nicolas Valfre", "ln.com/test", "test@gmail.com");
    public static final ApiInfo DEFAULT_API_INFO = new ApiInfo(
            "Test API Title", "Test API Description" , "1.1",
            "urn:tos",
            DEFAULT_CONTACT.getName(),
            "Apache 2.0",
            "http://www.apache.org/licenses/LICENSE-2.0"
    );
    // Swagger v2 -> /v2/api-docs
    // Swagger ui -> /swagger-ui.html
    @Bean
    public Docket api(){
        return
                new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(DEFAULT_API_INFO);
    }
}
