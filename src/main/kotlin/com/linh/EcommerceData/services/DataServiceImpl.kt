package com.linh.EcommerceData.services

import com.linh.EcommerceData.models.Record
import com.linh.EcommerceData.repositories.RecordRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile


@Service
class DataServiceImpl(
        @Autowired
        private val recordRepository: RecordRepository,
        @Autowired
        private val fileProcessingService: FileProcessingService
) : DataService {
    override fun getCountRecords(searchPhrase: String): Long {
        return recordRepository.findCountBySearchPhrase(searchPhrase)
    }

    override fun getRecords(searchPhrase : String, pageSize: Int, pageNumber : Int): List<Record> {
        return recordRepository.findBySearchPhrase(searchPhrase, PageRequest.of(pageNumber, pageSize))
    }

    override fun processFile(file: MultipartFile, updateId : String){
        fileProcessingService.processFileAsync(file.bytes, updateId)
    }

}