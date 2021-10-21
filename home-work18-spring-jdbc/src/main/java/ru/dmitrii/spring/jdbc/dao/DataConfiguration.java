package ru.dmitrii.spring.jdbc.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

    @Bean
    public SimpleJdbcInsert simpleJdbcInsertRecipe(JdbcTemplate jdbcTemplate) {
        return new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("RECIPES")
                .usingColumns("name")
                .usingGeneratedKeyColumns("id");
    }

    @Bean
    public SimpleJdbcInsert simpleJdbcInsertIngredient(JdbcTemplate jdbcTemplate) {
        return new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("INGREDIENTS")
                .usingColumns("name")
                .usingGeneratedKeyColumns("id");
    }

    @Bean
    public BufferedReader bufferedReader() {
        return new BufferedReader(new InputStreamReader(System.in));
    }

    @PostConstruct
    public void makeScript() throws SQLException {
        ScriptUtils.executeSqlScript(dataSource().getConnection(), new ClassPathResource("/data.sql"));
    }

    @PreDestroy
    public void clear () {
        try {
            bufferedReader().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
