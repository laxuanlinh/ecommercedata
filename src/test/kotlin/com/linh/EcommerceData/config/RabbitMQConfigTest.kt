package com.linh.EcommerceData.config

import org.junit.Assert
import org.junit.jupiter.api.Test


class RabbitMQConfigTest{

    private var config : RabbitMQConfig = RabbitMQConfig("host", "username", "password")

    @Test
    fun shouldReturnConnectionFactory(){
        Assert.assertNotNull(config.rabbitMQConnectionFactory())
    }
}