package com.linh.EcommerceData.models

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Record (
        @Column
        val invoiceNo : String? = null,
        @Column
        val stockCode : String? = null,
        @Column
        val description : String? = null,
        @Column
        val quantity : Long? = null,
        @Column
        val invoiceDate : String? = null,
        @Column
        val unitPrice : Double? = null,
        @Column
        val customerID : Long? = null,
        @Column
        val country : String? = null

){
    @Id
    @GeneratedValue
    val id: Long = 0
}