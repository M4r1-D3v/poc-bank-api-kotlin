package com.example.repositories.config

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@Configuration
@EnableAutoConfiguration
@Import(RepositoryConfig::class)
@ActiveProfiles(profiles = ["test", "postgresql"])
@ComponentScan(basePackages = ["com.example.repositories"])
open class RepositoryTestConfig