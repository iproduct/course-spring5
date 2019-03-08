#JSON PATCH Demo with Spring MVC, Spring Boot and WebTestClient (WebFlux)

The demo shows how to implement the [**RFC 6902 - JSON Patch**](http://tools.ietf.org/html/rfc6902), and [**RFC 7386 - JSON Merge Patch**](http://tools.ietf.org/html/rfc7386), using *Spring MVC* with *Jackson (2.2.x)* at its core.

The demo uses MongoDB repository. Mongo should be started before runnig the demo (e.g. *mongod --dbpath=c:\mongo-data*)
The mongo configuration sholud be updated in *resources/application.properties* file:
```
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=articles
```

## Running the demo
Just run the main Application class: *patchdemo/PatchDemoApplication*
You can test the app using Swagger UI at: http://localhost:8080/swagger-ui.html

Or using *curl*:
```
curl -X GET "http://localhost:8080/api/articles" -H "accept: */*"
```

The IDs in the following PATCH requests should be changed accordinly (using results from the list)
- for JSON Patch (RFC 6902):

```
curl -X PATCH "http://localhost:8080/api/articles/5c82dc5f72d32136e4df77be" -H "accept: application/json" -H "Content-Type: application/json-patch+json" -d "[{ \"op\": \"replace\", \"path\": \"/title\", \"value\": \"PATCHing Web with Spring\" }]"
```

- and for JSON Merge Patch (RFC 7386):

```
curl -X PATCH "http://localhost:8080/api/articles/5c82dc5f72d32136e4df77bf" -H "accept: application/json" -H "Content-Type: application/merge-patch+json" -d "{\"title\": \"PATCHing Web with Spring Again\"}"
```

