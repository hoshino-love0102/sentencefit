package com.sentencefit.adapter.`in`.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class CustomUserPrincipal(
    val userId: Long,
    private val email: String,
    private val authorities: Collection<GrantedAuthority>
) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> = authorities
    override fun getPassword(): String? = null
    override fun getUsername(): String = email
}