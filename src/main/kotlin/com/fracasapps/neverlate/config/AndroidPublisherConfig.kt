package com.fracasapps.neverlate.config

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.HttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.androidpublisher.AndroidPublisher
import com.google.api.services.androidpublisher.AndroidPublisherScopes
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class AndroidPublisherConfig{

    @Bean
    fun httpTransport(): HttpTransport {
        return GoogleNetHttpTransport.newTrustedTransport()
    }

    @Bean
    fun jsonFactory(): JsonFactory {
        return JacksonFactory.getDefaultInstance()
    }

    @Bean
    fun googleCredentials(httpTransport: HttpTransport, jsonFactory: JsonFactory): GoogleCredential {
        //for dev
//        val resource = resourceLoader.getResource("classpath:privateKey.json")
        val inputStream = System.getenv("GOOGLE_AUTH_JSON").byteInputStream()
        val credentials = GoogleCredential.fromStream(inputStream)
        return credentials.createScoped(Collections.singleton(AndroidPublisherScopes.ANDROIDPUBLISHER))
    }

    @Bean
    fun androidPublisher(httpTransport: HttpTransport, jsonFactory: JsonFactory, googleCredential: GoogleCredential): AndroidPublisher {
        return AndroidPublisher.Builder(httpTransport, jsonFactory, googleCredential).build()
    }
}