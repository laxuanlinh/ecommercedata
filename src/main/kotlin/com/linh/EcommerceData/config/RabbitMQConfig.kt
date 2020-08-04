package com.linh.EcommerceData.config

import com.rabbitmq.client.ConnectionFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class RabbitMQConfig(
        @Value("\${rabbitmq.host}")
        private val host : String,
        @Value("\${rabbitmq.username}")
        private val username : String,
        @Value("\${rabbitmq.password}")
        private val password : String
) {
    @Bean
    open fun rabbitMQConnectionFactory() : ConnectionFactory {
        val connectionFactory = ConnectionFactory()
        connectionFactory.host = this.host
        connectionFactory.username = this.username
        connectionFactory.password = this.password

        return connectionFactory
    }

}