package com.bugtsa.casher.casherresourceserver

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
class CasherResourceServerApplication
fun main(args: Array<String>) {
    SpringApplication.run(CasherResourceServerApplication::class.java, *args)
}
