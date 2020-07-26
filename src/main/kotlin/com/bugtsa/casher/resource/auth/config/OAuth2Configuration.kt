package com.bugtsa.casher.resource.auth.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.io.ClassPathResource
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.ClientDetailsService
import org.springframework.security.oauth2.provider.OAuth2RequestFactory
import org.springframework.security.oauth2.provider.endpoint.TokenEndpointAuthenticationFilter
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices
import org.springframework.security.oauth2.provider.token.DefaultTokenServices
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory
import javax.sql.DataSource

@Configuration
@EnableAuthorizationServer
class OAuth2Configuration : AuthorizationServerConfigurerAdapter() {

    @Value("\${check-user-scopes}")
    private var checkUserScopes: Boolean? = null

    @Autowired
    private lateinit var dataSource: DataSource

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    private lateinit var userDetailsService: UserDetailsService

    @Autowired
    private lateinit var clientDetailsService: ClientDetailsService

    @Autowired
    @Qualifier("authenticationManagerBean")
    private lateinit var authenticationManager: AuthenticationManager

    @Throws(Exception::class)
    override fun configure(oauthServer: AuthorizationServerSecurityConfigurer) {
        oauthServer
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
    }

    @Throws(Exception::class)
    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints
                .tokenServices(authorizationServerTokenServices())
                .tokenEnhancer(jwtAccessTokenConverter())
                .authenticationManager(authenticationManager).userDetailsService(userDetailsService)
        if (checkUserScopes!!)
            endpoints.requestFactory(requestFactory())
    }

    @Bean
    @Primary
    @Throws(java.lang.Exception::class)
    fun authorizationServerTokenServices(): AuthorizationServerTokenServices? {
        val tokenServices = DefaultTokenServices()
        tokenServices.setTokenStore(tokenStore())
        tokenServices.setSupportRefreshToken(true)
        tokenServices.setTokenEnhancer(jwtAccessTokenConverter())
        tokenServices.setAccessTokenValiditySeconds(DailyValidityAccessToken().validity)
        tokenServices.setRefreshTokenValiditySeconds(Int.MAX_VALUE)
        return tokenServices
    }

    @Bean
    fun tokenStore(): TokenStore {
        return JwtTokenStore(jwtAccessTokenConverter())
    }

    @Bean
    fun requestFactory(): OAuth2RequestFactory {
        val requestFactory = CustomOauth2RequestFactory(clientDetailsService)
        requestFactory.setCheckUserScopes(true)
        return requestFactory
    }

    @Bean
    fun jwtAccessTokenConverter(): JwtAccessTokenConverter {
        val converter = CustomTokenEnhancer()
        converter.setKeyPair(KeyStoreKeyFactory(ClassPathResource("jwt.jks"), "password".toCharArray()).getKeyPair("jwt"))
        return converter
    }

    @Throws(Exception::class)
    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients
                .jdbc(dataSource)
                .passwordEncoder(passwordEncoder)
    }

    @Bean
    fun tokenEndpointAuthenticationFilter(): TokenEndpointAuthenticationFilter {
        return TokenEndpointAuthenticationFilter(authenticationManager, requestFactory())
    }
}