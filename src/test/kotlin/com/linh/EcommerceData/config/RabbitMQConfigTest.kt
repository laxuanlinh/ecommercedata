package com.linh.EcommerceData.config

import org.junit.Assert
import org.junit.Before
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Value


class RabbitMQConfigTest{

    private var config : RabbitMQConfig = RabbitMQConfig("host", "username", "password")

    @Test
    fun shouldReturnConnectionFactory(){
        Assert.assertNotNull(config.rabbitMQConnectionFactory())
    }
}