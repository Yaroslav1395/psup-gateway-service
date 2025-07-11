# PSUP-GATEWAY-SERVICE

**PSUP-GATEWAY-SERVICE** — микросервис, отвечающий за единую точку входа для клиентов микросервисной архитектуры 
универсальной платформы дистрибуции товаров. Сервис маршрутизирует входящие запросы.

---

## 🔧 Возможности

- ✅ Реактивная архитектура
- ✅ Запуск без контейнера в режиме standalone
- ✅ Запуск в контейнере через профиль `local`. Config Server должен быть запущен. Если разворачивать в Docker, то бизнес сервисы должны быть запушены через профиль test. Иначе будут не в одной сети.
- ✅ Запуск в контейнере через профиль `test`. Вся инфраструктура должна быть запущена (Kafka, Config, Eureka и т.д.)
- ✅ Приложение запускается на порте **9999**
- ✅ Настроен circuit breaker на каждый сервис. Проверять события по http://localhost:9999/actuator/circuitbreakerevents
- ✅ Настроен Actuator. Для проверки маршрутизации http://localhost:9999/actuator/gateway/routes
- ✅ Настроена трассировка запросов. Смотреть по http://localhost:9411/
---

## 🛠️ Используемые технологии

| Технология                               | Назначение                                            |
|------------------------------------------|-------------------------------------------------------|
| **Spring WebFlux**                       | Реактивный веб-фреймворк для построения приложений    |
| **Spring Gateway**                       | Шлюз для маршрутизации и единой точки входа клиентов  |
| **Netflix Eureka Client**                | Для регистрации сервиса                               |
| **Spring Cloud Config Client**           | Для получение конфигурации запуска                    |
| **Spring Cloud Starter Bootstrap**       | Для запуска первоначального этапа конфигурации        |
| **Spring Retry**                         | Для обеспечения повторных попыток запроса при ошибках |
| **Spring Boot Starter AOP**              | Логирование, транзакции, retry через аннотации        |
| **Сircuit Breaker Reactor Resilience4j** | Защита от избыточных запросов на сломанный сервис     |
| **Actuator**                             | Контроль состояния приложения                         |
| **Zipkin Reporter Brave**                | Для отправки трассировки в Zipkin сервер              |
| **Micrometer Tracing Bridge Brave**      | Адаптер трассировок. Мост для Brave с Micrometer      |
| **Docker Mvn Plugin**                    | Для создания образа в docker                          |


---

## 🧪 Профили конфигурации

| Профиль                  | Назначение                                                                             | База данных |
|--------------------------|----------------------------------------------------------------------------------------|-------------|
| `application.yaml`       | Локальный запуск без Docker                                                            | -           |
| `bootstap-local.yaml`    | Локальный запуск в Docker (`docker-compose`). Config Server должен быть доступен.      | -           |
| `bootstap-test.yaml`     | Локальный запуск в Docker (`docker-compose`). Вся инфраструктура должна быть доступна  | -           |

---

## 🚀 Запуск

### 🔹 Локально (без Docker)

```bash
./mvnw spring-boot:run
```

### 🔹 Локально (Docker c Config Server).
Для очистки старых образов в docker:
```bash
docker image prune -f
```
Для сборки образа
```bash
mvn clean package -DskipTests docker:build
```
Для запуска контейнера
```bash
docker-compose -f docker-compose-local.yaml up --build
```

### 

### 🛠🔹 Запуск приложения локально с профилем test (без докера). Запуск локально в докере (через compose). Запуск в тестовой среде. При любом запуске вся инфраструктура должна быть доступна.
Для очистки старых образов в docker:
```bash
docker image prune -f
```
Для сборки образа
```bash
mvn clean package -DskipTests docker:build
```
Для создания общей сети внутри докер
```bash
if (-not (docker network inspect psup-shared-net -ErrorAction SilentlyContinue)) {docker network create psup-shared-net}
```
Для запуска контейнера
```bash
docker-compose -f docker-compose-test.yaml up --build
```