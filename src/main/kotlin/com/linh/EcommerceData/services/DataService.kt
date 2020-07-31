package com.linh.EcommerceData.services

import com.linh.EcommerceData.models.Record
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import reactor.core.publisher.Flux

interface DataService {

    fun processFile(file: MultipartFile, queueId : String)

    fun getRecords() : List<Record>
}