# SkillsRock IT-Аутстаффинг

## Тестовое задание на вакансию Java - Разработчик (developer)

Цель: Проверить, обладают ли кандидаты базовыми знаниями, необходимыми для обучения.

```bash
   ●​ Знание основ Java
   ●​ Понимание Spring
   ●​ Работа с Postgresql
```

## Задание

Постановка задачи: Написать REST API приложение которое будет предоставлять информацию о пользователе.

```bash
API:
  ●​ добавление нового пользователя(ФИО, тел.номер, Аватарка(url),Роль)
  ●​ получить информацию о пользователе
  ●​ обновить информацию о пользователе(изменить основные данные:
     ФИО, тел.номер, Аватарка(url), Роль)
  ●​ удалить пользователя по UUID(роль также должна быть удалена)
```

## Техническое описание задания

Стек технологий:

```bash
   ●​ Java 17
   ●​ Spring Boot 3
   ●​ PostgreSQL
```

Схема таблиц:

```bash
Users
-------------------------------------------------------------------
UUID     |    FIO     |    PhoneNumber   |   Avatar   |  Role(FK)
-------------------------------------------------------------------
         |  NOT NULL  |                  |            |
-------------------------------------------------------------------
Roles
-------------------------------------------------------------------
UUID(PK) |  RoleName  |
-------------------------------------------------------------------
         |  NOT NULL  |
-------------------------------------------------------------------
```

API-endpoints:

```bash
POST   /api/createNewUser (JSON BODY)
GET    /api/users?userID=anyUUID
PUT    /api/userDetailsUpdate (JSON BODY или Params)
DELETE /api/users?userID=anyUUID
```

Требования:

```bash
   ●​ читаемый и структурируемый код
   ●​ валидация приходящих данных
   ●​ SOLID, MVC
```

Будет плюсом:

```bash
   ●​   обработка ошибок через @ControllerAdvice
   ●​   добавление кэширования
   ●​   использование docker
   ●​   миграции с Liquibase
```

Критерии оценки

```bash
  ●​ Работоспособность API (CRUD операции).
  ●​ Качество кода и логичность структуры проекта.
  ●​ Корректная работа с PostgreSQL.
```


Время выполнения: 3 дня

## Подсказка:

Для сборки проекта spring: [https://start.spring.io/](https://start.spring.io/)

<img src="initializr.png" width="100%"/>

### Установка Java JDK

#### скачать установку [Java JDK](https://www.oracle.com/fr/java/technologies/downloads/) официальный сайт

### Установка PostgreSQL

#### скачать установку [PostgreSQL](https://www.enterprisedb.com/downloads/postgres-postgresql-downloads/) официальный сайт

#### параметры соединения с базой данных

###### apiusers/src/main/resources/application.properties

```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/users
spring.datasource.username=postgres
spring.datasource.password=password
```

###### созданиие базы данных users

```bash
CREATE DATABASE users;
```

### Запуск приложения Spring-boot REST API Web server

```bash
git clone https://github.com/SlavKoVrn/JavaApiPostgres apiusers
cd apiusers
mvnw spring-boot:run
```

### Запуск тестов приложения

```bash
cd apiusers
mvnw test
mvnw test -Dtest=skillsrock.apiusers.CascadeDeleteTest
mvnw test -Dtest=skillsrock.apiusers.UserServiceCacheTest
```

### Docker

компилировать иполняемый файл
 
#### target/apiusers-0.0.1-SNAPSHOT.jar

```bash
cd apiusers
mvnw clean package -DskipTests
```

#### запуск docker

```bash
cd apiusers

# Build the Spring Boot image
docker-compose build

# Start the services
docker-compose up -d
```

#### проверить базу данных

```bash
docker exec -it postgresdb psql -U postgres

psql (10.21 (Debian 10.21-1.pgdg90+1))
Type "help" for help.

postgres=# \c users
You are now connected to database "users" as user "postgres".

users=# SELECT * FROM roles;
 uuid | rolename
------+-----------
    1 | ADMIN
    2 | USER
    3 | MODERATOR
(3 rows)

users=# SELECT * FROM users;
 uuid |         fio         |   phonenumber    |                   avatar                   | role
------+---------------------+------------------+--------------------------------------------+------
    2 | Петр Петров         | +7(987)654-32-10 | https://www.kadastrcard.ru/img/avatar2.png |    2
    3 | Сидор Сидоров       | +7(912)345-67-89 | https://www.kadastrcard.ru/img/avatar3.png |    3
    1 | Вячеслав Колесников | +7(960)109-12-05 | https://www.kadastrcard.ru/img/slavko.png  |    2
(3 rows)
```


