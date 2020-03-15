package com.bugtsa.casher.resource.config

import javax.servlet.http.HttpServletResponse

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.TokenStore

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
class ResourceServerConfiguration : ResourceServerConfigurerAdapter() {

    @Autowired
    var tokenStore: TokenStore? = null

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests().anyRequest().permitAll().and().cors().disable().csrf().disable().httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint { request, response, authException -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED) }
                .accessDeniedHandler { request, response, authException -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED) }
    }

    @Throws(Exception::class)
    override fun configure(resources: ResourceServerSecurityConfigurer) {
        resources.resourceId("USER_ADMIN_RESOURCE").tokenStore(tokenStore)
    }

}