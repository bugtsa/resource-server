package com.bugtsa.casher.resource.auth.entity

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import javax.persistence.*

@Entity
class User : BaseIdEntity(), UserDetails {
    var email: String? = null
    private val username: String? = null
    private val password: String? = null
    private val enabled: Boolean = false

    @Column(name = "account_locked")
    private val accountNonLocked: Boolean = false

    @Column(name = "account_expired")
    private val accountNonExpired: Boolean = false

    @Column(name = "credentials_expired")
    private val credentialsNonExpired: Boolean = false

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_user", joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")], inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")])
    private val roles: List<Role>? = null

    override fun isEnabled(): Boolean {
        return enabled
    }

    override fun isAccountNonExpired(): Boolean {
        return !accountNonExpired
    }

    override fun isCredentialsNonExpired(): Boolean {
        return !credentialsNonExpired
    }

    override fun isAccountNonLocked(): Boolean {
        return !accountNonLocked
    }

    /*
     * Get roles and permissions and add them as a Set of GrantedAuthority
     */
    override fun getAuthorities(): kotlin.collections.Collection<GrantedAuthority> {
        val authorities = HashSet<GrantedAuthority>()

        roles!!.forEach { r ->
            authorities.add(SimpleGrantedAuthority(r.name))
            r.permissions?.forEach { p -> authorities.add(SimpleGrantedAuthority(p.name)) }
        }

        return authorities
    }

    override fun getPassword(): String? {
        return password
    }

    override fun getUsername(): String? {
        return username
    }

    companion object {

        private const val serialVersionUID = 1L
    }

}
