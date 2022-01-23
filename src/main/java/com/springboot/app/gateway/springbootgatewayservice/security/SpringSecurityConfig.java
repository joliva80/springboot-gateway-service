package com.springboot.app.gateway.springbootgatewayservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;


@Configuration
@EnableWebFluxSecurity
public class SpringSecurityConfig {
   /* 
    @Autowired
    private AuthenticationFilterJwt authenticationFilter;

    @Bean
    public SecurityWebFilterChain configure(ServerHttpSecurity http) {

        http
            .authorizeExchange()
        //    .anyExchange().permitAll()
            .pathMatchers("/api-oauth/**").permitAll()
            .pathMatchers(HttpMethod.GET, "/api-products/products", "/api-users/users", "api-items/items").permitAll()
            .pathMatchers(HttpMethod.GET, "/api-users/users/{id}").hasAnyRole("ADMIN","USER")
            .pathMatchers("/api-products/**", "/api-users/**", "/api-items/**").hasRole("ADMIN")
            .anyExchange().authenticated() // All routes authenticated
            .and()
            
            .addFilterAt(authenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION) // add the token user filter checker for authentication
            .csrf().disable(); // only needed for certain forms views

        return http.build();
    }
    */
 
}
