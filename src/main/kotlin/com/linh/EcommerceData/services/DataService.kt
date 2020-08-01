package com.linh.EcommerceData.services

import com.linh.EcommerceData.models.Record
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import reactor.core.publisher.Flux
import sun.security.x509.AccessDescription

interface DataService {

    fun processFile(file: MultipartFile, queueId : String)

    fun getRecords(searchPhrase: String, pageSize: Int, pageNumber : Int) : List<Record>

    fun getCountRecords(searchPhrase: String) : Long
}