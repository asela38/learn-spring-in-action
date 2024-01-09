package com;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
public class Config {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.first")
    public DataSourceProperties firstProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.second")
    @Qualifier("secondProperties")
    public DataSourceProperties secondProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource firstDs(DataSourceProperties firstProperties) {
        return firstProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    @Qualifier("secondDs")
    public DataSource secondDs(@Qualifier("secondProperties") DataSourceProperties secondProperties) {
        return secondProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    public JdbcTemplate firstJdbcTemplate(DataSource firstDs) {
        return new JdbcTemplate(firstDs);
    }

    @Bean
    public JdbcTemplate secondJdbcTemplate(@Qualifier("secondDs") DataSource secondDs) {
        return new JdbcTemplate(secondDs);
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer1(DataSource firstDs) {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("schema.sql"));
        resourceDatabasePopulator.addScript(new ClassPathResource("data.sql"));

        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(firstDs);
        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
        return dataSourceInitializer;
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer2(@Qualifier("secondDs") DataSource secondDs) {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("schema.sql"));
        resourceDatabasePopulator.addScript(new ClassPathResource("data.sql"));

        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(secondDs);
        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
        return dataSourceInitializer;
    }
}
