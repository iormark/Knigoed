package info.knigoed.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sql2o.Sql2o;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Value("${developer}")
    private String developer;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;

    @Bean
    public DataSource dataSource() {
        System.out.println("dataSource");
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        if (Boolean.parseBoolean(developer)) {
            dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/knigoed");
            dataSource.setUsername("knigoed");
            dataSource.setPassword("xxx");
            dataSource.setMaximumPoolSize(12);
        } else {
            dataSource.setJdbcUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            dataSource.setMaximumPoolSize(300);
        }
        return dataSource;
    }

    @Bean
    public Sql2o sql2o() {
        System.out.println("> Sql2o");
        return new Sql2o(dataSource());
    }

}
