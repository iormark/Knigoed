package info.knigoed.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.util.Properties;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {
    "info.knigoed.config",
    "info.knigoed.controller",
    "info.knigoed.service",
    "info.knigoed.manager",
    "info.knigoed.dao"})
public class WebConfig extends WebMvcConfigurerAdapter {

    @Value("${developer}")
    private String developer;

    // == RequestContext ==
    @Bean
    public RequestContext requestContext() {
        return new RequestContext();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestContext());
    }
    // ==


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**").addResourceLocations("/img/").setCachePeriod(3600 * 360);
        registry.addResourceHandler("/tmp/**").addResourceLocations("/tmp/").setCachePeriod(3600);
        registry.addResourceHandler("/builds/**").addResourceLocations("/builds/").setCachePeriod(3600 * 360);
        registry.addResourceHandler("/images/**").addResourceLocations("/images/").setCachePeriod(3600 * 360);
        registry.addResourceHandler("/favicon.ico").addResourceLocations("/");
        registry.addResourceHandler("/robots.txt").addResourceLocations("/");
        registry.addResourceHandler("/yourmood.gif").addResourceLocations("/");
        registry.addResourceHandler("/sitemap.xml").addResourceLocations("/");
    }

    @Bean
    public ViewResolver freemarkerViewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setCache(!Boolean.parseBoolean(developer));
        resolver.setSuffix(".ftl");
        resolver.setContentType("text/html; charset=UTF-8");
        return resolver;
    }

    @Bean
    public FreeMarkerConfigurer freemarkerConfig() {
        Properties settings = new Properties();
        settings.setProperty("number_format", "0.##");

        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        if (Boolean.parseBoolean(developer)) {
            freeMarkerConfigurer.setTemplateLoaderPath("/WEB-INF/views");
            settings.setProperty("template_exception_handler", "ignore");
            //settings.setProperty("template_exception_handler", "html_debug");
        } else {
            freeMarkerConfigurer.setTemplateLoaderPath("/WEB-INF/views/html-builds");
            settings.setProperty("template_exception_handler", "ignore");
        }

        freeMarkerConfigurer.setFreemarkerSettings(settings);
        return freeMarkerConfigurer;
    }

    @Bean
    public freemarker.template.Configuration freemarkerTemplate() {
        return freemarkerConfig().getConfiguration();
    }

}
