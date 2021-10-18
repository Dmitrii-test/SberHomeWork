package ru.dmitrii.spring.jdbc.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@ComponentScan
public class DataConfiguration {
    @Bean
    public DataSource dataSource() {
        return new DriverManagerDataSource("jdbc:h2:file:~/spring", "sa", "");
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @PostConstruct
    public void makeScript() throws SQLException {
        ScriptUtils.executeSqlScript(dataSource().getConnection(), new ClassPathResource("/data.sql"));
    }
}
