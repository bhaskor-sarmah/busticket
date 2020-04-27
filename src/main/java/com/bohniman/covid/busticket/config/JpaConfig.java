package com.bohniman.covid.busticket.config;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * JpaConfig
 */
@Configuration
public class JpaConfig {

    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(
                "jdbc:mysql://localhost:3306/busticket?characterEncoding=UTF-8&serverTimezone=IST&zeroDateTimeBehavior=convertToNull");
        dataSourceBuilder.username("root");
        dataSourceBuilder.password("root");
        return dataSourceBuilder.build();
    }
}