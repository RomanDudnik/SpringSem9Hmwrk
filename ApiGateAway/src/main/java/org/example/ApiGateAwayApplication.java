package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiGateAwayApplication {
    public static void main(String[] args) {

        SpringApplication.run(ApiGateAwayApplication.class, args);
    }

    /**
     * Метод для настройки маршрутов
     * @param builder
     * @return
     */
    @Bean
    public RouteLocator customLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Маршрут для заметок
                .route("NotesApplication", r -> r.path("/notes/**")
                        .uri("http://localhost:8080/"))
                // Маршрут для задач
                .route("TaskApplication", r -> r.path("/tasks/**")
                        .uri("http://localhost:8081/")).build();
    }
}