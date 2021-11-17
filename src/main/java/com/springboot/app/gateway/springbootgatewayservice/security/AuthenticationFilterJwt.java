package com.springboot.app.gateway.springbootgatewayservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilterJwt implements WebFilter{

    @Autowired
    private ReactiveAuthenticationManager authenticationManager;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // Get header bearer token
        return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                    .filter(authHeader -> authHeader.startsWith("Bearer ")) // Check if its the correct header
                    .switchIfEmpty(chain.filter(exchange).then(Mono.empty())) // end with nothing to do
                    .map(token -> token.replace("Bearer ", ""))  // else clean up work "Bearer " to get only the token part of the header value
                    .flatMap(token -> authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(null, token)))
                    .flatMap(authentication -> chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication)));
    }
    
}
