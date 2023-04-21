package com.cgm.registrootmongodb.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@EnableWebMvc
public class SwaggerConfig {

    /*@Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.cgm.registrootmongodb.controllers"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo())
                ;
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "Registro de OT API",
                "API para llevar un registro de OTs",
                "1.0",
                "",
                new Contact("CGMDEV", "https://cgmdevportafolio.vercel.app/",""),
                "LICENSE",
                "LICENSE URL",
                Collections.emptyList()
        );
    }*/
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Registro de OT API")
                        .description("API para llevar un registro de OTs \n Esta api solo recibe peticiones con token a travez de HEADER, favor de solicitar al administrador")
                        .version("v0.0.1")
                );
    }
}
