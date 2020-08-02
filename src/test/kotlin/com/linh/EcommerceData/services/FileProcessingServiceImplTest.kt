package com.linh.EcommerceData.services

import com.linh.EcommerceData.TestUtility
import com.linh.EcommerceData.exceptions.RabbitMQException
import com.linh.EcommerceData.models.Record
import com.linh.EcommerceData.repositories.RecordRepository
import com.rabbitmq.client.AMQP
import com.rabbitmq.client.AMQP.BasicProperties
import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.lang.RuntimeException

@RunWith(MockitoJUnitRunner::class)
class FileProcessingServiceImplTest {

    private val recordRepository = mock(RecordRepository::class.java)
    private val connectionFactory = mock(ConnectionFactory::class.java)
    private val connection = mock(Connection::class.java)
    private val channel = mock(Channel::class.java)
    private val declareOk = mock(AMQP.Queue.DeclareOk::class.java)
    private val bytes = ("InvoiceNo,StockCode,Description,Quantity,InvoiceDate,UnitPrice,CustomerID,Country\n" +
            "536365,85123A,WHITE HANGING HEART T-LIGHT HOLDER,6,12/1/2010 8:27,2.55,17850,United Kingdom").toByteArray()

    @InjectMocks
    private val fileProcessingServiceImpl = spy(FileProcessingServiceImpl(recordRepository, connectionFactory))

    @Before
    fun setUp(){
        `when`(connectionFactory.newConnection()).thenReturn(connection)
        `when`(connection.createChannel()).thenReturn(channel)
        doNothing().`when`(channel).close()
        `when`(recordRepository.save(TestUtility.any(Record::class.java))).thenReturn(Record())
    }

    @Test
    fun shouldReadFile(){
        fileProcessingServiceImpl.processFileAsync(bytes, "updateId")
    }

//    @Test(expected = RabbitMQException::class)
//    fun shouldThrowExceptionWhenPublishFails(){
//        Mockito.doThrow(RuntimeException()).`when`(channel).basicPublish(Mockito.anyString(),
//                                    Mockito.anyString(),
//                                    TestUtility.any(BasicProperties::class.java),
//                                    TestUtility.any(ByteArray::class.java))
//        fileProcessingServiceImpl.processFileAsync(bytes, "updateId")
//
//    }



}