package com.bugtsa.casher.resource.auth.config;

import com.bugtsa.casher.resource.auth.entity.User
import com.bugtsa.casher.resource.auth.repository.UserAuthRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import java.util.*

class CustomTokenEnhancer : JwtAccessTokenConverter() {

    @Autowired
    private lateinit var userAuthRepository: UserAuthRepository

    override fun enhance(accessToken: OAuth2AccessToken, authentication: OAuth2Authentication): OAuth2AccessToken {
        val user = if (authentication.principal is User) {
            authentication.principal as User
        } else {
            userAuthRepository.findByUsername(authentication.principal.toString())
        }

        val info = LinkedHashMap(accessToken.additionalInformation)

        info[EMAIL_FIELD] = user.email ?: EMAIL_DEFAULT_VALUE

        val customAccessToken = DefaultOAuth2AccessToken(accessToken)
        customAccessToken.additionalInformation = info

        return super.enhance(customAccessToken, authentication)
    }

    companion object {
        private const val EMAIL_FIELD = "email"
        private const val EMAIL_DEFAULT_VALUE = ""
    }
}