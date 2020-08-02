package com.linh.EcommerceData.services

import com.linh.EcommerceData.exceptions.RabbitMQException
import com.linh.EcommerceData.models.Record
import com.linh.EcommerceData.models.UpdateMessageDTO
import com.rabbitmq.client.CancelCallback
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DeliverCallback
import com.rabbitmq.client.Delivery
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.concurrent.Executors

@Service
class UpdateServiceImpl(
        @Autowired
        private val dataService : DataService,
        @Autowired
        private val rabbitMQConnectionFactory: ConnectionFactory
) : UpdateService{

    override fun emitRecords(searchPhrase : String, pageSize : Int, pageNumber : Int) : SseEmitter{
        val emitter = SseEmitter()
        val executors = Executors.newSingleThreadExecutor()
        executors.execute{
            val count = dataService.getCountRecords(searchPhrase)
            emitter.send(UpdateMessageDTO("50"))
            val records = dataService.getRecords(searchPhrase, pageSize, pageNumber)
            emitter.send(UpdateMessageDTO("100"))
            emitter.send(PageImpl<Record>(records, PageRequest.of(pageNumber, pageSize), count))
            emitter.complete()
        }

        executors.shutdown()

        return emitter
    }

    override fun emitFileUpdateProgress(updateId : String): SseEmitter {
        val emitter = SseEmitter()
        val executor = Executors.newSingleThreadExecutor()

        executor.execute {
            val connection = rabbitMQConnectionFactory.newConnection()
            val queueChannel = connection.createChannel()
            queueChannel.queueDeclare(updateId, false, false, false, null)
            val consumerTag = "SimpleConsumer"
            val deliverCallback = DeliverCallback { consumerTag: String?, delivery: Delivery ->
                val message = String(delivery.body, StandardCharsets.UTF_8)
                emitter.send(UpdateMessageDTO(message))
                if ("100".equals(message)){
                    emitter.complete()
                    executor.shutdown()
                }
            }
            val cancelCallback = CancelCallback { consumerTag: String? ->
                println("[$consumerTag] was canceled")
            }
            queueChannel.basicConsume(updateId, true, consumerTag, deliverCallback, cancelCallback)
        }
        executor.shutdown()
        return emitter
    }


}