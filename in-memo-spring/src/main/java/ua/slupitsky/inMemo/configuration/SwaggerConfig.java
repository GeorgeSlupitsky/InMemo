package ua.slupitsky.inMemo.configuration;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static Logger log = Logger.getLogger(SwaggerConfig.class.getName());

    @Bean
    public Docket myCollectionsApi(){
        log.info("Starting Swagger");

        Contact contact = new Contact(
                "Yegor Slupitskyi",
                "https://www.facebook.com/19postpunk91",
                "george.slupitsky@gmail.com"
        );

        List<VendorExtension> vext = new ArrayList<>();
        ApiInfo apiInfo = new ApiInfo(
                "Backend API",
                "App for controlling your collections",
                "1.0.0",
                null,
                contact,
                null,
                null,
                vext
        );

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .groupName("InMemo")
                .pathMapping("/")
                .apiInfo(apiInfo)
                .forCodeGeneration(true)
                .securityContexts(Lists.newArrayList(securityContext()))
                .securitySchemes(Lists.newArrayList(apiKey()))
                .useDefaultResponseMessages(false);

        docket = docket.select().apis(RequestHandlerSelectors.basePackage("ua.slupitsky.inMemo.controllers"))
                .build();
        log.info("Swagger started");
        return docket;
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(regex("ua.slupitsky.inMemo.controllers"))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(
                new SecurityReference("JWT", authorizationScopes));
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        String rootPath = System.getProperty("catalina.home");
        String imagePath = "file:" + rootPath + File.separator + "images/";
        registry.addResourceHandler("/images/**")
                .addResourceLocations(imagePath);
        log.info("Resource handlers added");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
