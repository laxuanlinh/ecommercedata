package com.linh.EcommerceData.services

import org.springframework.stereotype.Service
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@Service
interface UpdateService {

    fun emitRecords(searchPhrase : String, pageSize : Int, pageNumber : Int) : SseEmitter

    fun emitFileUpdateProgress(updateId : String) : SseEmitter
}