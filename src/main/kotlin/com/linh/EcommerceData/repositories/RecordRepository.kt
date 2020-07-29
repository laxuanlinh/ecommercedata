package com.linh.EcommerceData.repositories

import com.linh.EcommerceData.models.Record
import org.springframework.data.jpa.repository.JpaRepository

interface RecordRepository : JpaRepository<Record, Long>