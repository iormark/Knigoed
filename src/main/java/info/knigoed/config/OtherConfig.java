package info.knigoed.config;

import info.knigoed.isbn.ISBNCheck;
import info.knigoed.isbn.PrefixRanges;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.Properties;

@PropertySource("classpath:config.properties")
@Configuration
public class OtherConfig implements ServletContextAware {
    private ServletContext context;

    @Override
    public void setServletContext(ServletContext context) {
        this.context = context;
    }

    @Bean
    public PrefixRanges prefixRanges() throws Exception {
        return new PrefixRanges();
    }

    @Bean
    public ISBNCheck isbnCheck() throws Exception {
        return new ISBNCheck(prefixRanges());
    }

    @Bean
    public PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        //System.out.println("env : " + env);
        PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
        Properties properties = new Properties();

        // Файловая система 
        /*properties.setProperty("file.rootpath", context.getRealPath("/"));
        properties.setProperty("file.webinf", context.getRealPath("/WEB-INF"));
        properties.setProperty("file.tmppath", context.getRealPath("/tmp").replaceAll("(ROOT)/", ""));
        properties.setProperty("file.imgpath", context.getRealPath("/img").replaceAll("(ROOT)/", ""));
        pspc.setProperties(properties);*/

        return pspc;
    }


}
