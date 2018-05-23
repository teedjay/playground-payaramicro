# Payara Micro Playground
Payara Micro is a small [Java EE](http://www.oracle.com/technetwork/java/javaee/tech/index-jsp-142185.html) server 
that also includes support for [Eclipse MicroProfile 1.2](https://projects.eclipse.org/projects/technology.microprofile) 
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
This is a playground to mess around with different JEE7 and MicroProfile 1.2 features without any
additional libraries.  This should provide lean and mean WAR files, fast to copy and fast to deploy.

Each package in this project aims to demonstrate one feature and the different parts are [documented and explained](doc/index.adoc).

## TODO List
- [x] Add Oracle data-source example
- [x] Add Asciidoctor documentaton for each feaure (keep this main README lean and clean)

### To build the WAR file
You need Java 8 and Maven 8 installed to build and run.
```
mvn clean package
```

### To run locally
Make a local payara folder and download the latest [Payara Micro 5](https://www.payara.fish/payara_micro) jar file there (do **not** add payara micro to git). 

Then run the following command using Java 8.
```
java -jar payara/payara-micro-5.181.jar --deploy target/payaramicro.war
```

### To build and run as standalone "Uber JAR" / "FAT JAR"
```
mvn clean package
java -jar payara/payara-micro-5.181.jar --deploy target/payaramicro.war --outputUberJar my-standalone-app.jar
java -jar my-standalone-app.jar
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

### Swagger definition
When building the jaxrs-analyzer will inspect the actual bytecode and create swagger definition in `target/jaxrs-analyzer/swagger.json`.  
This definition can be imported into Postman or other tools for easy testing and can be used by clients to create client code.

The jaxrs-analyzer actually does some byte code analyzis and might be able to find out which entities you are
returning in your Response.   For more feature rich swagger generation and manual control you can look into 3rd party
generators like [Apiee](https://github.com/phillip-kruger/apiee/wiki) or use the latest Swagger 2.0 
[OpenApi 3.0 libraries](https://github.com/frantuma/swagger-core/wiki/Swagger-2.X---Getting-started). 


## Clustering
If you start multiple Payara Micro instanses they will automatically cluster together and share
session and cache data.  This feature is provided as default by [Hazelecast](https://hazelcast.org).