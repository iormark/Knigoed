package info.knigoed.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.sql2o.Sql2o;

import javax.sql.DataSource;
import java.sql.SQLException;

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

    @Value("${sphinx.url}")
    private String sphinxUrl;

    @Bean
    public DataSource dataSource() {
        System.out.println("dataSource");
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        // 6.x.x
        //dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaximumPoolSize(1);
        dataSource.setMaxLifetime(1000*5);
        return dataSource;
    }

    @Bean
    public Sql2o sql2o() {
        return new Sql2o(dataSource());
    }


    @Bean
    public DataSource mysqlDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaxTotal(1);
        dataSource.setMaxWaitMillis(1000 * 8);
        return dataSource;
    }

    @Bean(name = "mysqlJdbc")
    public JdbcTemplate mysqlJdbcTemplate() throws SQLException, ClassNotFoundException {
        return new JdbcTemplate(mysqlDataSource());
    }


    @Bean
    public DataSource sphinxDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername("");
        dataSource.setPassword("");
        dataSource.setUrl(sphinxUrl);
        dataSource.setMaxTotal(1);
        dataSource.setMaxWaitMillis(1000 * 8);
        return dataSource;
    }

    @Bean(name = "sphinxJdbc")
    public JdbcTemplate sphinxJdbcTemplate() throws SQLException, ClassNotFoundException {
        return new JdbcTemplate(sphinxDataSource());
    }

}
