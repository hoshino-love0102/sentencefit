package com.sentencefit.adapter.`in`.security

import com.sentencefit.application.auth.port.out.JwtIssuePort
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtIssuePort: JwtIssuePort
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorization = request.getHeader(HttpHeaders.AUTHORIZATION)

        if (!authorization.isNullOrBlank() && authorization.startsWith("Bearer ")) {
            val token = authorization.substring(7)

            if (jwtIssuePort.validate(token)) {
                val userId = jwtIssuePort.extractUserId(token)
                val principal = CustomUserPrincipal(
                    userId = userId,
                    email = "authenticated-user",
                    authorities = listOf(SimpleGrantedAuthority("ROLE_USER"))
                )

                val authentication = UsernamePasswordAuthenticationToken(
                    principal,
                    null,
                    principal.authorities
                )

                SecurityContextHolder.getContext().authentication = authentication
            }
        }

        filterChain.doFilter(request, response)
    }
}