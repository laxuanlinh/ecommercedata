package com.linh.EcommerceData.services

import com.rabbitmq.client.*
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UpdateServiceImplTest {

    private val dataService = Mockito.mock(DataService::class.java)
    private val connectionFactory = Mockito.mock(ConnectionFactory::class.java)
    private val connection = Mockito.mock(Connection::class.java)
    private val channel = Mockito.mock(Channel::class.java)

    private val updateService = spy(UpdateServiceImpl(dataService, connectionFactory))

    @Before
    fun setUp(){
        Mockito.`when`(connectionFactory.newConnection()).thenReturn(connection)
        Mockito.`when`(connection.createChannel()).thenReturn(channel)
    }

    @Test
    fun shouldEmitRecords(){
        assertNotNull(updateService.emitRecords("searchPhrase", 10, 0))
    }

    @Test
    fun shouldUpdateFileUploadProgress(){
        assertNotNull(updateService.emitFileUpdateProgress("updateId"))
    }


}