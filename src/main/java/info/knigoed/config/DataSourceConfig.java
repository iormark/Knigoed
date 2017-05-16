package info.knigoed.config;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sql2o.Sql2o;

@Configuration
public class DataSourceConfig {

	@Value("${developer}")
	private String developer;
	@Value("${datasource.url}")
	private String url;
	@Value("${datasource.username}")
	private String username;
	@Value("${datasource.password}")
	private String password;

	@Bean
	public DataSource dataSource() {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

		if (Boolean.parseBoolean(developer)) {
			dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/knigoed1");
			dataSource.setUsername("root");
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
