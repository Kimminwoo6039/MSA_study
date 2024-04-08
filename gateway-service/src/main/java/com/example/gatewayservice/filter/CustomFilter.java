package com.example.gatewayservice.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {
    private Logger logger = LoggerFactory.getLogger(CustomFilter.class);

    public CustomFilter() {
        super(Config.class);
    }

    public static class Config {
        // put the configuration properties
    }



    @Override
    public GatewayFilter apply(Config config) {
        // custom pre filter
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

         logger.info("Custom PRE fileter : request id - > {}" , request.getId());

         // custom post filter
            return  chain.filter(exchange).then(Mono.fromRunnable(() -> {
                logger.info("Custom POST fileter : response code - > {}",response.getStatusCode());
            }));
        };
    }

}
