# Order Service

Микросервис для управления корзиной, заказами и аутентификацией. Отправляет события в Kafka после оформления заказа.

## Технологии

- Java 17
- Spring Boot 3
- Spring Security (BCrypt, Basic Auth, Method Security)
- Spring Data JPA
- Apache Kafka
- PostgreSQL
- Lombok
- ModelMapper
- Docker / Docker Compose

## API

| Метод | Эндпоинт | Описание | Доступ |
|-------|----------|----------|--------|
| POST | `/auth/register` | Регистрация пользователя | все |
| GET | `/foods` | Список всех блюд | USER, ADMIN |
| GET | `/foods/{id}` | Блюдо по ID | USER, ADMIN |
| POST | `/foods` | Создать блюдо | ADMIN |
| PATCH | `/foods/{id}/edit` | Обновить блюдо | ADMIN |
| DELETE | `/foods/{id}` | Удалить блюдо | ADMIN |
| GET | `/bags` | Корзина текущего пользователя | USER, ADMIN |
| POST | `/bags` | Добавить блюдо в корзину | USER, ADMIN |
| PATCH | `/bags/{id}/edit` | Изменить количество | USER, ADMIN |
| DELETE | `/bags/{id}` | Удалить из корзины | USER, ADMIN |
| POST | `/orders/send` | Оформить заказ → очистить корзину → отправить в Kafka | USER, ADMIN |

## Запуск

```bash
docker-compose up -d
