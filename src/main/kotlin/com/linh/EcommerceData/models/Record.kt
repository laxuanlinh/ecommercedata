package com.linh.EcommerceData.models

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Record (
        val invoiceNo : String? = null,
        val stockCode : String? = null,
        val description : String? = null,
        val quantity : Long? = null,
        val invoiceDate : String? = null,
        val unitPrice : Double? = null,
        val customerID : Long? = null,
        val country : String? = null

){
    @Id
    @GeneratedValue
    val id: Long = 0
}