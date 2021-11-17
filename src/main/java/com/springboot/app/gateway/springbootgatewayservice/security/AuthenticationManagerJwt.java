package com.springboot.app.gateway.springbootgatewayservice.security;

import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationManagerJwt implements ReactiveAuthenticationManager {

    @Value("${config.security.oauth.jwt.key}")  // From github config server file
    private String jwtkey;

    @Override
    @SuppressWarnings("unchecked")
    public Mono<Authentication> authenticate(Authentication authentication) {
        
        return Mono.just(authentication.getCredentials().toString()) // just turns any class into a reactive stream
                    .map(token -> {
                        // Get key
                        SecretKey key = Keys.hmacShaKeyFor(Base64.getEncoder().encode(jwtkey.getBytes()));  // add BAse 64 to hmac to make it more strong, encoded from oauth service AuthorizationServerConfig.java

                        // validate access and return result
                        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody(); // claims are the roles accessess
                    })
                    .map(claims -> {
                        // Username
                        String username = claims.get("user_name", String.class);
                        // Get Roles
                        List<String> roles = claims.get("authorities", List.class);
                        // Get Accessess
                        Collection<GrantedAuthority> authorities = roles.stream().map(role -> new SimpleGrantedAuthority(role))
                                                                                 .collect(Collectors.toList());
                        // Return authentication filter checker
                        return new UsernamePasswordAuthenticationToken(username, null, authorities);
                    });
    }
    
}
