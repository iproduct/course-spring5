#JSON PATCH Demo with Spring MVC, Spring Boot and WebTestClient (WebFlux)

The demo shows how to implement the [**RFC 6902 - JSON Patch**](http://tools.ietf.org/html/rfc6902), and [**RFC 7386 - JSON Merge Patch**](http://tools.ietf.org/html/rfc7386), using *Spring MVC* with *Jackson (2.2.x)* at its core.

The demo uses MongoDB repository. Mongo should be started before runnig the demo (e.g. mongod --dbpath=c:\mongo-data)
The mongo configuration sholud be updated in *resources/application.properties* file:
```
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=articles
```

