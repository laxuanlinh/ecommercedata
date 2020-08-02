package com.linh.EcommerceData.controllers

import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito.*
import org.springframework.ui.Model

class FileControllerTest {

    private val fileController = FileController()

    @Test
    fun shouldGetIndex(){
        assertEquals("index", fileController.index())
    }



}