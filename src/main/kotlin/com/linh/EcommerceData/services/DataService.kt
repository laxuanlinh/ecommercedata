package com.linh.EcommerceData.services

import com.linh.EcommerceData.models.Record
import org.springframework.web.multipart.MultipartFile

interface DataService {

    fun processFile(file: MultipartFile, queueId : String)

    fun getRecords(searchPhrase: String, pageSize: Int, pageNumber : Int) : List<Record>

    fun getCountRecords(searchPhrase: String) : Long
}