package com.vincle.vfs.filters;


//import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;


/**
 * THIS FILTER APPLIES ON ALL THE REQUESTS/RESPONSES GLOBALLY
 */
@Component
public class GlobalFilters implements GlobalFilter {

    private final Logger log = LoggerFactory.getLogger(GlobalFilters.class);
    
    /* REACTIVE PROGRAMMING WITH WEBFLUX */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Executing PRE request filter");

        //exchange.getRequest().mutate().headers(h -> h.add("token", "12345")); // Add header name token to the req headers

        return chain.filter(exchange) // Continue with the next filter in the chain
                                    .then(Mono.fromRunnable(() -> {   // Once finished all the chain, lets continue with a POST business logic
            log.info("Executing POST requet filter");

            //Optional.ofNullable(exchange.getRequest().getHeaders().getFirst("token")).ifPresent(value -> {
            //    exchange.getResponse().getHeaders().add("token", value); // Functional expression to fetch token value from req header and add it to the response headers
            //});

            //exchange.getResponse().getCookies().add("color", ResponseCookie.from("color", "red").build()); // passing a cookie into the response as a post change
            //exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);  
        })); 
    }
    
}
