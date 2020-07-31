package com.linh.EcommerceData

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication
@EnableAsync
class ECommerceDataApplication

fun main(args: Array<String>) {
	runApplication<ECommerceDataApplication>(*args)
}
