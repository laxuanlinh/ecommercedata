package com.linh.EcommerceData.models

import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class RecordTest {

    @Test
    fun shouldInitRecord(){
        val record = Record("invoiceNo",
                "stockCode",
                "description",
                10,
                "02/08/2020 15:11",
                10.0,
                1L,
                "country")

        assertNotNull(record)
        assertEquals("invoiceNo", record.invoiceNo)
        assertEquals("stockCode", record.stockCode)
        assertEquals(10, record.quantity)
        assertEquals("02/08/2020 15:11", record.invoiceDate)
        assertEquals(10.0, record.unitPrice)
        assertEquals(1L, record.customerID)
        assertEquals("country", record.country)
        assertEquals(0, record.id)
    }

}