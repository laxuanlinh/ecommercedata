package com.linh.EcommerceData.controllers

import com.linh.EcommerceData.services.DataService
import com.rabbitmq.client.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.io.IOException
import java.util.concurrent.Executors
import java.nio.charset.StandardCharsets
import java.util.*


@RestController
@RequestMapping("/api")
class DataRestController(
        @Autowired
        private val dataService : DataService,
        @Autowired
        private val rabbitMQConnectionFactory: ConnectionFactory
) {

    @GetMapping("/file_process_update/{updateId}")
    fun updateFileProcess(@PathVariable("updateId") updateId: String): SseEmitter {
        val emitter = SseEmitter()
        val executor = Executors.newSingleThreadExecutor()

        executor.execute {
            try {
                val connection = rabbitMQConnectionFactory.newConnection()
                val queueChannel = connection.createChannel()
                queueChannel.queueDeclare(updateId, false, false, false, null)
                val consumerTag = "SimpleConsumer"
                val deliverCallback = DeliverCallback { consumerTag: String?, delivery: Delivery ->
                    val message = String(delivery.body, StandardCharsets.UTF_8)
                    emitter.send(message)
                    if ("100".equals(message)){
                        emitter.complete()
                        executor.shutdown()
                    }
                }
                val cancelCallback = CancelCallback { consumerTag: String? ->
                    println("[$consumerTag] was canceled")
                }
                queueChannel.basicConsume(updateId, true, consumerTag, deliverCallback, cancelCallback)
            } catch (e: IOException) {
                emitter.completeWithError(e)
            }
        }
        executor.shutdown()
        return emitter
    }

    @PostMapping("/file")
    fun uploadFile(@RequestParam("file") file: MultipartFile, redirectAttributes: RedirectAttributes): String {
        val uuid : String = UUID.randomUUID().toString()
        dataService.processFile(file, uuid)
        return uuid
    }
}