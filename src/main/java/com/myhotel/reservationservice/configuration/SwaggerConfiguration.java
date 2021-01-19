package com.myhotel.reservationservice.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.any;

@Configuration
@EnableSwagger2
@AllArgsConstructor
@PropertySource("classpath:application.yml")
public class SwaggerConfiguration {

    Environment env;

   @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .enableUrlTemplating(true)
                .useDefaultResponseMessages(false)
                .select().apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
            return new ApiInfoBuilder()
                    .title(env.getProperty("spring.application.name"))
                    .description(env.getProperty("spring.application.description"))
                    .version(env.getProperty("spring.application.version"))
                    .build();
    }


}
