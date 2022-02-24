package com.innova.graduationproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi(){
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        //Call the apiInfo method to create an ApiInfo instance.
        //Inside is the information displayed in the document
        docket.apiInfo(apiInfo())
                .enable(true)
                .select()
                //Control instances under exposed paths
                //If an interface does not want to be exposed, you can use a comment
                .apis(RequestHandlerSelectors.basePackage("com.innova.graduationproject"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //Page Title
                .title("SpringMVC Swagger")
                .description("Controller Interface Testing")
                .contact(new Contact("Contact us","","evren.rahimoglu@gmail.com"))
                .version("1.1")
                .build();
    }

}