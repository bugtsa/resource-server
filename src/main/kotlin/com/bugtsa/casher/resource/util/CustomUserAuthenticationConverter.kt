package com.bugtsa.casher.resource.util

import com.bugtsa.casher.resource.model.CustomPrincipal
import java.util.LinkedHashMap

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter.AUTHORITIES
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter.USERNAME
import org.springframework.util.StringUtils

class CustomUserAuthenticationConverter : UserAuthenticationConverter {

    private val EMAIL = "email"

    private var defaultAuthorities: Collection<GrantedAuthority>? = null

    fun setDefaultAuthorities(defaultAuthorities: Array<String>) {
        this.defaultAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(StringUtils.arrayToCommaDelimitedString(defaultAuthorities))
    }

    override fun convertUserAuthentication(userAuthentication: Authentication): Map<String, *> {
        val response = LinkedHashMap<String, Any>()
        response[USERNAME] = userAuthentication.name

        if (userAuthentication.authorities != null && !userAuthentication.authorities.isEmpty())
            response[AUTHORITIES] = AuthorityUtils.authorityListToSet(userAuthentication.authorities)

        return response
    }

    override fun extractAuthentication(map: Map<String, *>): Authentication? {
        return if (map.containsKey(USERNAME)) UsernamePasswordAuthenticationToken(
                CustomPrincipal(map[USERNAME].toString(), map[EMAIL].toString()), "N/A",
                getAuthorities(map)) else null
    }

    private fun getAuthorities(map: Map<String, *>): Collection<GrantedAuthority>? {
        if (!map.containsKey(AUTHORITIES))
            return defaultAuthorities

        val authorities = map[AUTHORITIES]

        if (authorities is String)
            return AuthorityUtils.commaSeparatedStringToAuthorityList(authorities)

        if (authorities is Collection<*>)
            return AuthorityUtils.commaSeparatedStringToAuthorityList(
                    StringUtils.collectionToCommaDelimitedString(authorities))

        throw IllegalArgumentException("Authorities must be either a String or a Collection")
    }

}