# customer-orders-api

Application uses [Java 22](https://docs.oracle.com/en/java/javase/22/), [Spring boot 3.3.2](https://spring.io/blog/2024/07/18/spring-boot-3-3-2-available-now) and [gradle 8.8](https://docs.gradle.org/8.8/release-notes.html)


[Open docs](http://localhost:8080/api-docs)

[Open swagger](http://localhost:8080/swagger-ui.html)

[Open console](http://localhost:8080/console)


## Default credentials:

* username: `admin`

* password: `admin`


## Spring profiles:

* default: `mysql is required`

* local: `mysql is not required. The application uses h2 in memory.`


### Environment variables

| Environment variable | Default value   |
| ------ |-----------------|
| API_USER | admin           |
| API_PASSWORD | admin           |
| DATABASE_HOST | localhost       |
| DATABASE_PORT | 3306            |
| DATABASE_DB | customer-orders |
| DATABASE_USER | root            |
| DATABASE_PASSWORD | Vr*SJ3<@~2        |

### Docker

Preconfigured databese for build in docker-compose

```sh
$ cd docker
$ docker-compose run -d
# wait Wait a few minutes for the database container to initialize.

# Script to enable localhost communication in mysql container
$ chmod +x ./init.sh
$ ./init.sh
```

### Security

Spring security configured, all requests pass through the spring security interface, it is possible to change the access credentials through environment variables. The default credentials are username: admin password: admin

| Environment variable |
| ------ |
| API_USER |
| API_PASSWORD |


### Architecture
Architecture based on [Onion Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)


### Tests

#### Unit

* [Junit5](https://junit.org/junit5/)
* [Mockito](https://site.mockito.org/)
* [Spring-boot-starter-test](https://docs.spring.io/spring-boot/docs/1.5.7.RELEASE/reference/html/boot-features-testing.html)


#### Integration

* [io.karatelabs](https://www.karatelabs.io/)
