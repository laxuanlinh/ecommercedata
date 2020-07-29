package com.linh.EcommerceData.services

import com.linh.EcommerceData.models.Record
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
interface DataService {

    fun processFile(file: MultipartFile)

    fun getRecords() : List<Record>
}