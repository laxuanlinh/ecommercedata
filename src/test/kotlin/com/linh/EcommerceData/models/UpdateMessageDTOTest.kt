package com.linh.EcommerceData.models

import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class UpdateMessageDTOTest {
    @Test
    fun shouldInitUpdateMessage(){
        val updateMessageDTO = UpdateMessageDTO("message")
        assertEquals("message", updateMessageDTO.message)
    }
}