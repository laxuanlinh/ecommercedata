package com.linh.EcommerceData.controllers

import com.linh.EcommerceData.models.Record
import com.linh.EcommerceData.models.UpdateMessageDTO
import com.linh.EcommerceData.services.DataService
import com.rabbitmq.client.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
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

    @GetMapping("/records")
    fun getRecords(@RequestParam("search") searchPhrase : String,
                   @RequestParam("size")pageSize : Int,
                   @RequestParam("pageNumber")pageNumber: Int) : SseEmitter{
        val emitter = SseEmitter()
        val executors = Executors.newSingleThreadExecutor()

        executors.execute{
            try {
                val count = dataService.getCountRecords(searchPhrase)
                emitter.send(UpdateMessageDTO("50"))
                val records = dataService.getRecords(searchPhrase, pageSize, pageNumber)
                emitter.send(UpdateMessageDTO("100"))
                emitter.send(PageImpl<Record>(records, PageRequest.of(pageNumber, pageSize), count))
            } catch (e: IOException){
                emitter.completeWithError(e)
            } finally {
                emitter.complete()
            }
        }

        executors.shutdown()
        return emitter
    }

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