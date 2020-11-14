package com.bugtsa.casher.resource

import com.bugtsa.casher.resource.config.DataBaseConfig.Companion.createDataBaseConfig
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import java.util.*

@SpringBootApplication
class CasherResourceServerApplication

fun main(args: Array<String>) {
    val application = SpringApplication(CasherResourceServerApplication::class.java)

    application.setDefaultProperties(getProperties())
    application.run(*args)
}

private fun getProperties(): Properties {
    val urlDatabase = System.getenv("JDBC_DATABASE_URL")
    val userNameDatabase = System.getenv("JDBC_DATABASE_USERNAME")
    val passwordDatabase = System.getenv("JDBC_DATABASE_PASSWORD")
    val dataBaseConfig = createDataBaseConfig(
            url = urlDatabase,
            userName = userNameDatabase,
            password = passwordDatabase
    )
    return Properties().apply {
        put("spring.datasource.url", dataBaseConfig.url)
        put("spring.datasource.username", dataBaseConfig.userName)
        put("spring.datasource.password", dataBaseConfig.password)
        put("spring.datasource.driver-class-name", "com.mysql.jdbc.Driver")
        put("spring.datasource.tomcat.test-while-idle", false)
        put("spring.datasource.tomcat.validation-query", "SELECT 1")
        put("spring.datasource.initialization-mode", "never")
        put("spring.datasource.platform", "mysql")
        put("spring.datasource.hikari.connectionTimeout", 30000)
        put("spring.datasource.hikari.idleTimeout", 600000)
        put("spring.datasource.hikari.maxLifetime", 1800000)
        put("check-user-scopes", true)
        put("server.port", 9091)
        put("security.oauth2.resource.jwt.key-value",
                "    -----BEGIN PUBLIC KEY-----\n" +
                        "    MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgnEzp25qlRJqe/IWf88o\n" +
                        "    lfkOl9a7tX4wgDrLLzguzMIAHdLv2do1qb48nPJPJYElD6wVTguokOTOND+4oFzJ\n" +
                        "    Oob/t/6cQ65gPCXVo8OfN1jrternBEIu9t2FuSmn/Ica+JvIZ4NJFz1UnxY1fYGv\n" +
                        "    WMjsMwpOZk8uU39Pu8ywyyaRPXYi0Sbk8vj35GjdwkhshBOUaE45JlZpBms6HerY\n" +
                        "    45TBbHTiC9hFQ8slwEd8DIsLYhSlogmCC7hciOVlNEJxUoumq1M20wlizkWhxCyM\n" +
                        "    Q/5OZMLDBzhK7+lgp9M3ZUdJFOgNKbkR/gdktWC4RZxp90t+Ztct6pHoNz5K/0ji\n" +
                        "    ZwIDAQAB\n" +
                        "    -----END PUBLIC KEY-----")
    }
}
