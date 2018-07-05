# Payara Micro Playground
Payara Micro is a small [Java EE](http://www.oracle.com/technetwork/java/javaee/tech/index-jsp-142185.html) server 
that also includes support for [Eclipse MicroProfile 1.3](https://projects.eclipse.org/projects/technology.microprofile/releases/microprofile-1.3) 
and [JCache](https://www.jcp.org/en/jsr/detail?id=107).

Payara Micro have built-in clustering as default (no configuration needed) and can run WAR files directly
from the command line without any installation.  This makes it suitable for fast deploy cycles and running
MicroServices.

Since you compile against a specification that is provided by the runtime you will get skinny WAR files.
The small size makes it smooth and fast to do CI/CD and is optimal for storage of deployment artifacts in
repositories like Nexus. 

Skinny war files makes Payara Micro a great fit with Docker and container orchestration like Kubernetes.
The only change between versioned Docker images will be the tiny WAR file.

Payara Micro also facilitates Fat-JAR / Uber-JAR, creating a single runnable jar-artifact
that includes both appserver and your application(s).  This is great for easy deployment. 

## What is this project?
This is a playground to mess around with different JEE7 and MicroProfile 1.3 features without any
additional libraries.  This should provide lean and mean WAR files, fast to copy and fast to deploy.

Each package in this project aims to demonstrate one feature and the different parts are [documented and explained](doc/index.adoc).

## TODO List
- [x] Add Docker instructions
- [x] Add Oracle data-source example
- [x] Add Asciidoctor documentaton for each feaure (keep this main README lean and clean)
- [x] MicroProfile Fault Tolerance 1.0 (Retry, Timeout, Fallback, Bulkhead, Circuit Breaker)
- [x] MicroProfile OpenAPI 1.0
- [x] MicroProfile Metrics 1.1
- [x] MicroProfile Health Check 1.0
- [ ] MicroProfile Config 1.2 (existing example is 1.1 only)
- [ ] MicroProfile OpenTracing 1.0
- [ ] MicroProfile Rest Client 1.0 (Type-safe Rest Client 1.0)
- [ ] MicroProfile JWT Auth 1.0 (JWT Propagation 1.0)
- [ ] Explain how to do proper logging with Payara (JUL and SLF4J, combined in server log or separate)
- [ ] Replace System.out and System.err with proper logging
- [ ] Build asciidoc PDF and HTML automatically
- [ ] Add .editorconfig
- [ ] Add Jenkinsfile

### To build the WAR file
You need Java 8 and Maven 8 installed to build and run.
```
mvn clean package
```

### To run locally on Payara Micro
Make a local payara folder and download the latest [Payara Micro 5](https://www.payara.fish/payara_micro) jar file there (do **not** add payara micro to git). 

Then run the following command using Java 8.
```
java -jar payara/payara-micro-5.182.jar --deploy target/payaramicro.war
```

### To build and run as standalone "Uber JAR" / "FAT JAR"
```
mvn clean package
java -jar payara/payara-micro-5.182.jar --deploy target/payaramicro.war --outputUberJar my-standalone-app.jar
java -jar my-standalone-app.jar
```

### Optionally : Run on OpenLiberty port 9080 with hot deploy features (and debugging if needed)
```
mvn clean package liberty:run-server -Popenliberty
mvn clean package liberty:debug-server -Popenliberty    <== Use "Attach to Local Process" in IDEA after server starts
```

### Optionally : Compile, build and run using Docker
The build command will first compile and create the war file in a Java 8 and Maven container.  
Then war file will then be copied into the result image tagged as `my_payara_image` which can be run like shown below.
```
docker build -t my_payara_image .
docker run -p 8080:8080 -d --name playgound_payara my_payara_image
```

After running the container you can stop and start it like this :
```
docker stop playgound_payara
docker start playgound_payara
```

### OpenAPI v3.0.0 / Swagger definition
The MicroProfile 1.3 specification includes [OpenAPI 1.0 ](http://download.eclipse.org/microprofile/microprofile-open-api-1.0/microprofile-openapi-spec.html).  
This produces documentaton in the openapi 3.0.0 format, take a look in the `openapi` package to see how this work.

For a demo start the application and check out this url : curl http://localhost:8080/openapi

For a list of tools and utilities regarding OpenAPI take a look here : https://apis.guru/awesome-openapi3/

## Clustering
If you start multiple Payara Micro instanses they will automatically cluster together and share
session and cache data.  This feature is provided as default by [Hazelecast](https://hazelcast.org).