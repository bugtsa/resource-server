package com.bugtsa.casher.resource.config

data class DataBaseConfig(
        val url: String,
        val userName: String,
        val password: String
) {

    companion object {
        fun createDataBaseConfig(
                url: String,
                userName: String,
                password: String
        ) = DataBaseConfig(
                url = url,
                userName = userName,
                password = password
        )
    }
}