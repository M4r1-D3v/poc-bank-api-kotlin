package com.example.repositories.config

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = ["com.example.repositories"])
open class RepositoryConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    open fun dataSource(): DataSource =
        DataSourceBuilder.create().build()
}