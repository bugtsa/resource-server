package com.bugtsa.casher.casherresourceserver.config


import com.bugtsa.casher.casherresourceserver.model.CustomPrincipal
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.MethodParameter
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebSecurity
class WebMvcConfiguration : WebMvcConfigurer {

    override fun addArgumentResolvers(argumentResolvers: MutableList<HandlerMethodArgumentResolver>) {
        argumentResolvers.add(currentUserHandlerMethodArgumentResolver())
    }

    @Bean
    fun currentUserHandlerMethodArgumentResolver(): HandlerMethodArgumentResolver {
        return object : HandlerMethodArgumentResolver {
            override fun supportsParameter(parameter: MethodParameter): Boolean {
                return parameter.parameterType == CustomPrincipal::class.java
            }

            @Throws(Exception::class)
            override fun resolveArgument(parameter: MethodParameter, mavContainer: ModelAndViewContainer?,
                                         webRequest: NativeWebRequest, binderFactory: WebDataBinderFactory?): Any? {
                try {
                    return SecurityContextHolder.getContext().authentication.principal as CustomPrincipal
                } catch (e: Exception) {
                    return null
                }

            }
        }
    }
}