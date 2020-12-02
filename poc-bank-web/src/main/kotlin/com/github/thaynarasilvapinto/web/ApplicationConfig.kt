package com.example.web

import com.example.repositories.config.RepositoryConfig
import com.example.service.config.ServiceConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import


@SpringBootApplication
@Configuration
@Import(
    RepositoryConfig::class,
    ServiceConfig::class
)
@ComponentScan(basePackages = ["com.example"])
open class ApplicationConfig