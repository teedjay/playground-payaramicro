# Payara Micro Playground
Payara Micro is a small [Java EE 7](http://www.oracle.com/technetwork/java/javaee/tech/index-jsp-142185.html) server 
that also includes support for [Eclipse MicroProfile 1.2](https://projects.eclipse.org/projects/technology.microprofile) 
and [JCache](https://www.jcp.org/en/jsr/detail?id=107).

Payara Micro have built-in clustering as default (no configuration needed) and can run WAR files directly
from the command line without any installation.  This makes it suitable for fast deploy cycles and running
MicroServices.

It is also a good fit with Docker and container orchestration like Kubernetes.

## What is this project?
This is a playground to mess around with different JEE7 and MicroProfile 1.2 features without any
additional libraries.  This should provide lean and mean WAR files, fast to copy and fast to deploy.

Each package in this project aims to demonstrate one feature and the different parts are explained below.

### To build the WAR file
```
mvn clean package
```

### To run locally
Make a local payara folder and download the latest [Payara Micro](https://www.payara.fish/payara_micro) jar file there (do **not** add payara micro to git). 

Then run the following command using Java 8.
```
java -jar payara/payara-micro-4.1.2.181.jar --deploy target/payaramicro.war
```



## JEE7 features
This section demonstrate the standard JEE7 features.

### Testable REST services with JAX-RS
With standard JEE7 we can create powerful REST services that are easy to test.
A simple example that can store and retrieve quotes is included in the package `quotes`.

The example has full test coverage and demonstrates how to test JAX-RS.  It also 
demonstrate use of very simple Java classes with public fields as data-transport for
automatic mapping between Java and JSON.
```
curl -i http://localhost:8080/payaramicro/rest/quotes
curl -i http://localhost:8080/payaramicro/rest/quotes/<uuid>
curl -i -X DELETE http://localhost:8080/payaramicro/rest/quotes/<uuid>
curl -i -X POST -H "Content-Type: application/json" -d '{"quote":"Eat Your Food","author":"Your Mother"}' http://localhost:8080/payaramicro/rest/quotes
```

### Security as a layer
It is easy to add security as a layer to your application.  In the `security` package we
have a filter called BasicAuthenticationLayer that demos how to protect a url and inject
information about who is authenticated directly into the REST service.
 ```
curl -I http://localhost:8080/payaramicro/rest/secure                   <= 401 Unauthorized
curl -i --user ole:bole http://localhost:8080/payaramicro/rest/secure   <= 200 OK
 ```



## MicroProfile 1.2 features
These features are not yet included in this playground `Fault Tolerance 1.0`, `JWT Propagation 1.0`, 
`Health Metrics 1.0`, `Health Check 1.0`.

### Config API 1.1
By using the Config API it is possible to inject configuration directly into your code.
Default config override order is System Properties -> Environment Variables -> microprofile-config.properties

In the package `config` you'll find a small webservice that returns a config read from the properties file :
```
curl http://localhost:8080/payaramicro/rest/config
```



## JCache JSR-107
The JCache is only enabled when Payara Micro runs in cluster mode (which is default).
It can be tested with the following curl commands :
```
curl http://localhost:8080/payaramicro/rest/cache/123
curl -X DELETE http://localhost:8080/payaramicro/rest/cache/123
curl -X DELETE http://localhost:8080/payaramicro/rest/cache/all
```



## Clustering
If you start multiple Payara Micro instanses they will automatically cluster together and share
session and cache data.  This feature is provided as default by [Hazelecast](https://hazelcast.org).