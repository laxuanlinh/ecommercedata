package com.linh.EcommerceData.controllers

import com.linh.EcommerceData.TestUtility
import com.linh.EcommerceData.services.DataService
import com.linh.EcommerceData.services.UpdateService
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

class DataRestControllerTest {

    private val dataService = mock(DataService::class.java)
    private val updateService = mock(UpdateService::class.java)

    private val multipartFile = mock(MultipartFile::class.java)

    private val controller = DataRestController(dataService, updateService)

    @Before
    fun setUp(){
        doNothing().`when`(dataService).processFile(TestUtility.any(MultipartFile::class.java), anyString())
        `when`(updateService.emitRecords(anyString(), anyInt(), anyInt())).thenReturn(SseEmitter())
        `when`(updateService.emitFileUpdateProgress(anyString())).thenReturn(SseEmitter())
    }

    @Test
    fun shouldGetEmitterRecords(){
        assertNotNull(controller.getRecords("searchPhrase", 10, 0))
    }

    @Test
    fun shouldGetEmitterUpdateFileProgress(){
        assertNotNull(controller.updateFileProcess("updateId"))
    }

    @Test
    fun shouldUploadFile() {
        assertNotNull(controller.uploadFile(multipartFile))
    }

}