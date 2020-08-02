package com.linh.EcommerceData.services

import com.linh.EcommerceData.exceptions.RabbitMQException
import com.linh.EcommerceData.models.Record
import com.linh.EcommerceData.repositories.RecordRepository
import com.opencsv.CSVReader
import com.rabbitmq.client.ConnectionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.lang.Exception
import java.nio.charset.StandardCharsets
import kotlin.math.roundToLong

@Service
class FileProcessingServiceImpl (
        @Autowired
        private val recordRepository: RecordRepository,
        @Autowired
        private val rabbitMQConnectionFactory : ConnectionFactory
) : FileProcessingService{

    @Async
    override fun processFileAsync(bytes: ByteArray, updateId : String) {
        val csvReader = this.getCSVReader(bytes)
        val totalLineCount = this.getTotalLineCount(bytes)
        var currentLineCount = 1L
        var currentPercentage = 0L

        val connection  = rabbitMQConnectionFactory.newConnection()
        val queueChannel = connection.createChannel()
        queueChannel.queueDeclare(updateId, false, false, false, null)

        var nextLine : Array<String>
        this.skipHeader(csvReader)
            do {
                nextLine = csvReader.readNext() ?: break
                val record = this.getRecordFromLine(nextLine)
                recordRepository.save(record)
                currentLineCount++
                if (this.currentPercentageIncreasesBy(currentLineCount, totalLineCount, currentPercentage, 5)){
                    currentPercentage = (100*currentLineCount.toDouble() / totalLineCount).roundToLong()
                    queueChannel.basicPublish("", updateId, null, currentPercentage.toString().toByteArray(StandardCharsets.UTF_8))
                }
            } while (true)
        queueChannel.close()
    }

    private fun currentPercentageIncreasesBy(currentLineCount : Long, totalLineCount : Long, currentPercentage : Long, increaseBy : Int) : Boolean{
        return (100*currentLineCount / totalLineCount) - currentPercentage >= increaseBy
    }

    private fun getCSVReader(bytes: ByteArray) : CSVReader {
        val bufferedReader = this.getBufferedReader(bytes)
        return CSVReader(bufferedReader)
    }

    private fun getBufferedReader(bytes: ByteArray) : BufferedReader{
        val fileInputStream  = ByteArrayInputStream(bytes)
        return BufferedReader(InputStreamReader(fileInputStream))
    }

    private fun getTotalLineCount(bytes: ByteArray) : Long {
        val bufferedReader = this.getBufferedReader(bytes)
        return bufferedReader.lines().count()
    }

    private fun getRecordFromLine(nextLine : Array<String>) : Record = Record(nextLine[0],
                nextLine[1],
                nextLine[2],
                nextLine[3].toLongOrNull() ?: 0,
                nextLine[4],
                nextLine[5].toDoubleOrNull() ?: 0.0,
                nextLine[6].toLongOrNull() ?: 0,
                nextLine[7])

    private fun skipHeader(csvReader : CSVReader){
        csvReader.readNext()
    }

}