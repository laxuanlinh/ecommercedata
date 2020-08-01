package com.linh.EcommerceData.repositories

import com.linh.EcommerceData.models.Record
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface RecordRepository : JpaRepository<Record, Long> {

    @Query("select r from Record r where r.stockCode like CONCAT('%',:searchPhrase,'%') or r.description like CONCAT('%',:searchPhrase,'%')")
    fun findBySearchPhrase(@Param("searchPhrase")searchPhrase : String, pageable: Pageable) : List<Record>

    @Query("select count(r) from Record r where r.stockCode like CONCAT('%',:searchPhrase,'%') or r.description like CONCAT('%',:searchPhrase,'%')")
    fun findCountBySearchPhrase(@Param("searchPhrase")searchPhrase : String) : Long
}