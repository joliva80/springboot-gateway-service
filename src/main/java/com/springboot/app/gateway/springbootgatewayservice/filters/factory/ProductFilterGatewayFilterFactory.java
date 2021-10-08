package com.springboot.app.gateway.springbootgatewayservice.filters.factory;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;


/**
 * THIS IS A FILTER FOR A PARTICULAR CASES AND NEED TO BE LINKED WITHIN THE APPLICATION.YML AS A FILTER.
 */
@Component
public class ProductFilterGatewayFilterFactory extends AbstractGatewayFilterFactory<ProductFilterGatewayFilterFactory.Configuration> {
    
    private static final Logger logger = LoggerFactory.getLogger(ProductFilterGatewayFilterFactory.class);


    public ProductFilterGatewayFilterFactory() {
        super(Configuration.class);
    }


    @Override
    public GatewayFilter apply(Configuration config) {
        return (exchange, chain) -> {
            logger.info("Executing PRE GatewayFilterFactory: " + config.message);
            
            // PRE filters code block
            // code filter here...

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                logger.info("Executing POST GatewayFilterFactory: " + config.message);
                
                // POST filters code block 
                // code filter here...
                Optional.ofNullable(config.cookieValue).ifPresent(cookie -> {
                    exchange.getResponse().addCookie(ResponseCookie.from(config.cookieName, cookie).build());
                });
                
            }));
        };
    }

    /**
     * Inner class for configurations
     */
    public static class Configuration{
        private String message;
        private String cookieValue;
        private String cookieName;

        public String getMessage(){
            return message;
        }

        public String getCookieValue(){
            return cookieValue;
        }

        public String getCookieName(){
            return cookieName;
        }

        public void setMessage(String m){
            this.message = m;
        }

        public void setCookieValue(String v){
            this.cookieValue = v;
        }

        public void setCookieName(String n){
            this.cookieName = n;
        }
    }
}
