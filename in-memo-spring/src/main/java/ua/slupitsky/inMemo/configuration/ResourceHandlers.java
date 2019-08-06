package ua.slupitsky.inMemo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.util.logging.Logger;

@Configuration
@EnableWebMvc
public class ResourceHandlers implements WebMvcConfigurer {

    private static final Logger logger = Logger.getLogger(ResourceHandlers.class.getName());

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String rootPath = System.getProperty("catalina.home");
        String imagePath = "file:" + rootPath + File.separator + "images/";
        registry.addResourceHandler("/images/**").addResourceLocations(imagePath);
        logger.info("Resource handlers added");
    }

}
