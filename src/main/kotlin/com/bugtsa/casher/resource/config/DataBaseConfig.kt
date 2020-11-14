package com.bugtsa.casher.resource.config

data class DataBaseConfig(
        val url: String,
        val userName: String,
        val password: String
) {

    companion object {
        fun createDataBaseConfig() = DataBaseConfig(
                url = URL_DATABASE,
                userName = USERNAME_DATABASE,
                password = PASSWORD_DATABASE
        )
    }
}