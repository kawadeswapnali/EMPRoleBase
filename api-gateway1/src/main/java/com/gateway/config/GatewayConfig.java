package com.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gateway.filter.JwtFilter;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder,
                                           JwtFilter jwtFilter) {

        return builder.routes()

                .route("employee-service", r -> r
                        .path("/employee/**")
                        .filters(f ->
                                f.filter(jwtFilter.apply(new JwtFilter.Config())))
                        .uri("http://localhost:8081"))

                .build();
    }
}