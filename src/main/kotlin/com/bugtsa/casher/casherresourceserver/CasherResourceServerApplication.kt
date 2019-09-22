package com.bugtsa.casher.casherresourceserver

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class CasherResourceServerApplication

fun main(args: Array<String>) {
    SpringApplication.run(CasherResourceServerApplication::class.java, *args)
}
