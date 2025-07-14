package sakhno.psup.gateway_service.config.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * Фильтр для добавления логов входящих и исходящих HTTP-запросов
 */
@Component
public class LoggingWebFilter implements WebFilter {
    private static final Logger log = LoggerFactory.getLogger(LoggingWebFilter.class);

    /**
     * Логирует входящий HTTP-запрос и исходящий HTTP-ответ с указанием метода, пути, статуса и времени выполнения.
     *
     * @param exchange текущий HTTP-запрос и ответ
     * @param chain цепочка фильтров Gateway
     * @return реактивный поток, представляющий завершение обработки запроса
     */
    @Override
    public @NonNull Mono<Void> filter(ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        long startTime = System.currentTimeMillis();

        ServerHttpRequest request = exchange.getRequest();
        String method = request.getMethod().toString();
        String path = request.getURI().getRawPath();
        String query = request.getURI().getRawQuery();
        String fullUrl = path + (query != null ? "?" + query : "");

        log.info("→ Gateway [{} {}]  headers={}", method, fullUrl, request.getHeaders());

        return chain.filter(exchange)
                .doOnSuccess(done -> {
                    long duration = System.currentTimeMillis() - startTime;
                    exchange.getResponse().getStatusCode();
                    int status = exchange.getResponse().getStatusCode().value();
                    log.info("← Gateway [{} {}] status={} duration={}ms", method, fullUrl, status, duration);
                });
    }
}
