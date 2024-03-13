package com.mikes.pedido.application.core.domain.security

import org.springframework.context.annotation.Bean
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity.HeaderSpec
import org.springframework.security.config.web.server.ServerHttpSecurity.HeaderSpec.ContentSecurityPolicySpec
import org.springframework.security.web.server.SecurityWebFilterChain
import java.time.Duration

@Bean
fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain? {
    http
        .headers { headers: HeaderSpec ->
            headers
                .contentTypeOptions { cto ->
                    cto.disable()
                }
                .hsts { hsts ->
                    hsts.maxAge(Duration.ofSeconds(31536000))
                    hsts.includeSubdomains(true)
                }
                .contentSecurityPolicy { contentSecurityPolicy: ContentSecurityPolicySpec ->
                    contentSecurityPolicy
                        .policyDirectives("script-src 'self'")
                }
        }
    return http.build()
}
