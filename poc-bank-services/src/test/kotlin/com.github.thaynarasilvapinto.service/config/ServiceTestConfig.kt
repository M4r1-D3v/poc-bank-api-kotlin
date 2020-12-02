package com.example.service.config

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@Configuration
@EnableAutoConfiguration
@Import(ServiceConfig::class)
@ComponentScan(basePackages = ["com.example.service"])
open class ServiceTestConfig