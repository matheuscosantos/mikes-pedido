package com.mikes.pedido.application.core.domain.security

import org.springframework.context.annotation.Bean
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity.HeaderSpec
import org.springframework.security.config.web.server.ServerHttpSecurity.HeaderSpec.ContentSecurityPolicySpec
import org.springframework.security.web.server.SecurityWebFilterChain

@Bean
fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain? {
    http
        .headers { headers: HeaderSpec ->
            headers
                .contentSecurityPolicy { contentSecurityPolicy: ContentSecurityPolicySpec ->
                    contentSecurityPolicy
                        .policyDirectives("script-src 'self'")
                }
        }
    return http.build()
}
