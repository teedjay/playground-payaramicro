# Payara Micro Playground
Payara Micro is a small [Java EE 7](http://www.oracle.com/technetwork/java/javaee/tech/index-jsp-142185.html) server 
that also includes support for [Eclipse MicroProfile 1.2](https://projects.eclipse.org/projects/technology.microprofile) 
and [JCache](https://www.jcp.org/en/jsr/detail?id=107).

Payara Micro have built-in clustering as default (no configuration needed) and can run WAR files directly
from the command line without any installation.  This makes it suitable for fast deploy cycles and running
MicroServices.

It is also a good fit for use together with Docker and container orchestration like Kubernetes.

## What is this project?
This is a playground to mess around with different JEE7 and MicroProfile 1.2 features without any
additional libraries.  This should provide lean and mean WAR files, that is fast to copy and fast to deploy.

## To build the WAR file
```
mvn clean package
```

## To run locally
Make a local payara folder and download the latest [Payara Micro](https://www.payara.fish/payara_micro) jar file there (do **not** add payara micro to git). 

Then run the following command using Java 8.
```
java -jar payara/payara-micro-4.1.2.181.jar --deploy target/PayaraMicroPlayground.war
```


## JEE7 features
With standard JEE7 we can make powerful REST services.  A simple example is included in the code as
package `quoteservice`.


## MicroProfile 1.2 features

### Config API 
By using the Config API it is possible to inject configuration directly into your code.
Default config override order is System Properties -> Environment Variables -> microprofile-config.properties

In the package `config` you'll find a small webservice that returns a config read from the properties file :
```
curl http://localhost:8080/sitatserver/rest/config
```



## JCache JSR-107 feature
This is only enabled when Payara Micro runs in cluster mode (which is default).
java -jar payara-micro-4.1.2.181.jar --deploy target/sitatserver.war

curl http://localhost:8080/sitatserver/rest/cache/123
curl -X DELETE http://localhost:8080/sitatserver/rest/cache/123
curl -X DELETE http://localhost:8080/sitatserver/rest/cache/all


## Clustering



This Payara Server 5 Beta release contains each of the 5 specs associated with MicroProfile 1.2:
Config 1.1 (slight update to Config 1.0)
Fault Tolerance 1.0
JWT Propagation 1.0
Health Metrics 1.0
Health Check 1.0
previous content from MicroProfile 1.1



CheatSheet

mvn archetype:generate -Dfilter=com.airhacks:

java -jar payara-micro.jar --nocluster --deploy target/quoteserver.war

curl -i http://localhost:8080/quoteserver/rest/quotes

curl -i --user ole:bole http://localhost:8080/quoteserver/rest/quotes

curl --user ole:bole http://localhost:8080/quoteserver/rest/quotes | jq


