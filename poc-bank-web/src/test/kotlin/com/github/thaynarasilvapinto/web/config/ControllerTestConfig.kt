package com.example.web.config

import com.example.web.ApplicationConfig
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(ApplicationConfig::class)
open class ControllerTestConfig