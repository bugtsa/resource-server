package com.bugtsa.casher.casherresourceserver.util

import java.util.Arrays
import java.util.Date
import java.util.HashMap
import java.util.LinkedHashSet

import org.springframework.boot.autoconfigure.security.oauth2.resource.JwtAccessTokenConverterConfigurer
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.OAuth2Request
import org.springframework.security.oauth2.provider.token.AccessTokenConverter
import org.springframework.security.oauth2.provider.token.AccessTokenConverter.*
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.stereotype.Component

@Component
class CustomAccessTokenConverter : AccessTokenConverter, JwtAccessTokenConverterConfigurer {

    private var includeGrantType: Boolean = false

    private var userTokenConverter: UserAuthenticationConverter = CustomUserAuthenticationConverter()

    override fun configure(converter: JwtAccessTokenConverter) {
        converter.setAccessTokenConverter(this)
    }

    override fun extractAccessToken(value: String, map: Map<String, *>): OAuth2AccessToken {
        val token = DefaultOAuth2AccessToken(value)
        val info = HashMap<String, Any?>(map)

        info.remove(EXP)
        info.remove(AUD)
        info.remove(CLIENT_ID)
        info.remove(SCOPE)

        if (map.containsKey(EXP))
            token.setExpiration(Date(map[EXP] as Long * 1000L))

        if (map.containsKey(JTI))
            info[JTI] = map[JTI]


        token.setScope(extractScope(map))
        token.setAdditionalInformation(info)
        return token
    }


//    private fun extractScope(map: Map<String, *>): Set<String> {
//        var scope = emptySet<String>()
//        if (map.containsKey(AccessTokenConverter.SCOPE)) {
//            val scopeObj = map[AccessTokenConverter.SCOPE]
//            if (String::class.java.isInstance(scopeObj))
//                scope = LinkedHashSet(Arrays.asList(*String::class.java.cast(scopeObj).split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()))
//            else if (Collection<*>::class.java.isAssignableFrom(scopeObj.javaClass)) {
//                val scopeColl = scopeObj as Collection<String>
//                scope = LinkedHashSet(scopeColl)
//            }
//        }
//        return scope
//    }
    @SuppressWarnings("unchecked")
    private fun extractScope(map: Map<String, *>): Set<String> {
        var scope = emptySet<String>()
        if (map.containsKey(AccessTokenConverter.SCOPE)) {
            val scopeObj = map[AccessTokenConverter.SCOPE]
            if (String::class.java.isInstance(scopeObj))
                scope = LinkedHashSet(Arrays.asList(*String::class.java.cast(scopeObj).split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()))
            else if (scopeObj is String && Collection::class.java.isAssignableFrom(scopeObj.javaClass)) {
                val scopeColl = scopeObj as Collection<String>
                scope = LinkedHashSet(scopeColl)
            }
        }
        return scope
    }

    override fun extractAuthentication(map: Map<String, *>): OAuth2Authentication {
        val scope = extractScope(map)
        val parameters = HashMap<String, String>()
        val user = userTokenConverter.extractAuthentication(map)

        val clientId = map[CLIENT_ID] as String
        parameters[CLIENT_ID] = clientId

        if (includeGrantType && map.containsKey(GRANT_TYPE))
            parameters[GRANT_TYPE] = map[GRANT_TYPE] as String

        val resourceIds = LinkedHashSet(
                if (map.containsKey(AUD)) getAudience(map) else emptySet())

        var authorities: Collection<GrantedAuthority>? = null

        if (user == null && map.containsKey(AUTHORITIES)) {
            val roles = (map[AUTHORITIES] as Collection<String>).toTypedArray()
            authorities = AuthorityUtils.createAuthorityList(*roles)
        }

        val request = OAuth2Request(parameters, clientId, authorities, true, scope, resourceIds, null, null, null)

        return OAuth2Authentication(request, user)
    }


    private fun getAudience(map: Map<String, *>): Collection<String> {
        val auds = map[AUD]

        return if (auds is Collection<*>) {
            auds as Collection<String>
        } else setOf(auds as String)

    }

    override fun convertAccessToken(token: OAuth2AccessToken, authentication: OAuth2Authentication): Map<String, *> {
        val response = HashMap<String, Any?>()
        val clientToken = authentication.getOAuth2Request()
        if (!authentication.isClientOnly())
            response.putAll(userTokenConverter.convertUserAuthentication(authentication.getUserAuthentication()))
        else if (clientToken.getAuthorities() != null && !clientToken.getAuthorities().isEmpty())
            response[UserAuthenticationConverter.AUTHORITIES] = AuthorityUtils.authorityListToSet(clientToken.getAuthorities())

        if (token.getScope() != null)
            response[SCOPE] = token.getScope()

        if (token.getAdditionalInformation().containsKey(JTI))
            response[JTI] = token.getAdditionalInformation().get(JTI)

        if (token.getExpiration() != null)
            response[EXP] = token.getExpiration().getTime() / 1000

        if (includeGrantType && authentication.getOAuth2Request().getGrantType() != null)
            response[GRANT_TYPE] = authentication.getOAuth2Request().getGrantType()
        response.putAll(token.getAdditionalInformation())
        response[CLIENT_ID] = clientToken.getClientId()
        if (clientToken.getResourceIds() != null && !clientToken.getResourceIds().isEmpty())
            response[AUD] = clientToken.getResourceIds()
        return response
    }


    fun setUserTokenConverter(userTokenConverter: UserAuthenticationConverter) {
        this.userTokenConverter = userTokenConverter
    }

    fun setIncludeGrantType(includeGrantType: Boolean) {
        this.includeGrantType = includeGrantType
    }


}