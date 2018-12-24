package com.fracasapps.neverlate.config

import com.fracasapps.neverlate.models.Secrets
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@PropertySource("classpath:application.properties")
class SecretConfig{
    @Value("\${here.appId}")
    private lateinit var hereAppId: String

    @Value("\${here.appCode}")
    private lateinit var hereAppCode: String

    @Bean
    fun secrets(): Secrets {
        return Secrets(hereAppId, hereAppCode)
    }
}