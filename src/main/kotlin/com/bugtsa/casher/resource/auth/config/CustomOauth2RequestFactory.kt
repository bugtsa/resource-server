package com.bugtsa.casher.resource.auth.config;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.oauth2.provider.ClientDetails
import org.springframework.security.oauth2.provider.ClientDetailsService
import org.springframework.security.oauth2.provider.TokenRequest
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory
import org.springframework.security.oauth2.provider.token.TokenStore

class CustomOauth2RequestFactory(clientDetailsService: ClientDetailsService) : DefaultOAuth2RequestFactory(clientDetailsService) {

    @Autowired
    private lateinit var tokenStore: TokenStore

    @Autowired
    private lateinit var userDetailsService: UserDetailsService

    override fun createTokenRequest(requestParameters: Map<String, String>,
                                    authenticatedClient: ClientDetails): TokenRequest {
        if (requestParameters[GRANT_TYPE] == REFRESH_TOKEN) {
            val authentication = tokenStore.readAuthenticationForRefreshToken(
                    tokenStore.readRefreshToken(requestParameters[REFRESH_TOKEN]))
            SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(authentication.name, null,
                    userDetailsService.loadUserByUsername(authentication.name).authorities)
        }
        return super.createTokenRequest(requestParameters, authenticatedClient)
    }

    companion object {
        private const val GRANT_TYPE = "grant_type"
        private const val REFRESH_TOKEN = "refresh_token"
    }
}
