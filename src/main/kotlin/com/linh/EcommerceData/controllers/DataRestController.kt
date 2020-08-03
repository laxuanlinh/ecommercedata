package com.linh.EcommerceData.controllers

import com.linh.EcommerceData.models.UpdateMessageDTO
import com.linh.EcommerceData.services.DataService
import com.linh.EcommerceData.services.UpdateService
import com.linh.EcommerceData.services.UpdateServiceImpl
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
        private val updateService: UpdateService
) {

    @GetMapping("/records")
    fun getRecords(@RequestParam("search") searchPhrase : String,
                   @RequestParam("size")pageSize : Int,
                   @RequestParam("pageNumber")pageNumber: Int) : SseEmitter{

        return updateService.emitRecords(searchPhrase, pageSize, pageNumber)
    }

    @GetMapping("/file_process_update/{updateId}")
    fun updateFileProcess(@PathVariable("updateId") updateId: String): SseEmitter {
        return updateService.emitFileUpdateProgress(updateId)
    }

    @PostMapping("/file")
    fun uploadFile(@RequestParam("file") file: MultipartFile): String {
        val uuid : String = UUID.randomUUID().toString()
        dataService.processFile(file, uuid)
        return uuid
    }

}