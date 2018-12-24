package com.fracasapps.neverlate.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class DefaultConfig{

    @Bean
    fun restController(): RestTemplate {
        return RestTemplate()
    }
}