package com.linh.EcommerceData.services

interface FileProcessingService {

    fun processFileAsync(bytes: ByteArray, updateId : String)

}