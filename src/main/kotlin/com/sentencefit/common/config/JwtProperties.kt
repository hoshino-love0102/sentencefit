package com.sentencefit.common.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
    var secret: String = "",
    var accessTokenExpirationSeconds: Long = 3600,
    var refreshTokenExpirationSeconds: Long = 1209600
)