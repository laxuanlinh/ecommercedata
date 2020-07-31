package com.linh.EcommerceData.config

import com.rabbitmq.client.Channel
import com.rabbitmq.client.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfig {

    @Bean
    fun rabbitMQConnectionFactory() : ConnectionFactory = ConnectionFactory()

}