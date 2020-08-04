package com.linh.EcommerceData.services

import com.linh.EcommerceData.TestUtility
import com.linh.EcommerceData.repositories.RecordRepository
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.data.domain.Pageable
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class DataServiceImplTest {

    private val recordRepository = mock(RecordRepository::class.java)
    private val fileProcessingService = mock(FileProcessingService::class.java)
    private val dataService = DataServiceImpl(recordRepository, fileProcessingService)
    private val multipartFile = mock(MultipartFile::class.java)

    @Before
    fun setUp(){
        `when`(recordRepository.findBySearchPhrase(anyString(), TestUtility.any(Pageable::class.java))).thenReturn(Arrays.asList())
        `when`(recordRepository.findCountBySearchPhrase(anyString())).thenReturn(0L)
        doNothing().`when`(fileProcessingService).processFileAsync(TestUtility.any(ByteArray::class.java), anyString())
        `when`(multipartFile.bytes).thenReturn(ByteArray(3))
    }

    @Test
    fun shouldGetCountRecords(){
        assertEquals(0, dataService.getCountRecords("searchPhrase"))
    }

    @Test
    fun shouldGetRecordList(){
        assertEquals(0, dataService.getRecords("searchPhrase", 10, 0).size)
    }

    @Test
    fun shouldProcessFile(){
        dataService.processFile(multipartFile, "updateId")
    }

}