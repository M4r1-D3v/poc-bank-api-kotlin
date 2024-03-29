package com.example.repositories.config

import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@ContextConfiguration
@SpringBootTest(classes = [RepositoryTestConfig::class])
abstract class RepositoryBaseTest