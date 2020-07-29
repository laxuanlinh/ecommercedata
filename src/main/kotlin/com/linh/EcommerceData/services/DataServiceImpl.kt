package com.linh.EcommerceData.services

import com.linh.EcommerceData.FileProcessingHelper
import com.linh.EcommerceData.models.Record
import com.linh.EcommerceData.repositories.RecordRepository
import com.opencsv.CSVReader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStreamReader
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.concurrent.Future


@Service
class DataServiceImpl(
        @Autowired
        private val recordRepository: RecordRepository,
        @Autowired
        private val fileProcessingHelper: FileProcessingHelper
) : DataService {

    override fun getRecords(): List<Record> {
        return recordRepository.findAll()
    }

    override fun processFile(file: MultipartFile){
        val future: Future<String> = fileProcessingHelper.processFileAsync(file)
    }

}