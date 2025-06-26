package sakhno.psup.gateway_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

//TODO: переделать под единый объект ответа
@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/product-service")
    public Mono<String> fallbackForProductService() {
        return Mono.just("Сервис каталога сломан. Ожидайте восстановления");
    }

    @GetMapping("/manufacture-service")
    public Mono<String> fallbackForManufactureService() {
        return Mono.just("Сервис производства сломан. Ожидайте восстановления");
    }

    @GetMapping("/storage-service")
    public Mono<String> fallbackForStorageService() {
        return Mono.just("Сервис склада сломан. Ожидайте восстановления");
    }
}
