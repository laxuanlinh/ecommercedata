package com.linh.EcommerceData

import com.linh.EcommerceData.models.Record
import com.linh.EcommerceData.repositories.RecordRepository
import com.opencsv.CSVReader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.AsyncResult
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStreamReader
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.concurrent.Future

@Component
class FileProcessingHelper (
        @Autowired
        private val recordRepository: RecordRepository
){

    @Async
    fun processFileAsync(file : MultipartFile) : Future<String> {
        var bytes: ByteArray = file.bytes
        val fileInputStream  = ByteArrayInputStream(bytes)
        var bufferedReader  = BufferedReader(InputStreamReader(fileInputStream))
        val csvReader  = CSVReader(bufferedReader)
        var nextLine : Array<String>
        csvReader.readNext()
        var i:Int = 0;
        do {
            i++
            if (i>20)
                break
            nextLine = csvReader.readNext() ?: break

            val record = Record(nextLine[0],
                    nextLine[1],
                    nextLine[2],
                    nextLine[3].toLongOrNull() ?: 0,
                    LocalDate.parse(nextLine[4], DateTimeFormatter.ofPattern("MM/d/yyyy H:mm")),
                    nextLine[5].toDoubleOrNull() ?: 0.0,
                    nextLine[6].toLongOrNull() ?: 0,
                    nextLine[7])
            println(record.invoiceNo)
            recordRepository.save(record)
        } while (true)

        return AsyncResult<String>("File processing finished")
    }

}