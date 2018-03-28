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

## TODO List
- [x] Add Oracle data-source example
- [ ] Add each demonstrated feature to separate markdown (keep this main README lean and clean)



### To build the WAR file
```
mvn clean package
```

### To run locally
Make a local payara folder and download the latest [Payara Micro](https://www.payara.fish/payara_micro) jar file there (do **not** add payara micro to git). 

Then run the following command using Java 8.
```
java -jar payara/payara-micro-5.181.jar --deploy target/payaramicro.war
```

### Swagger definition
When building the jaxrs-analyzer will inspect the actual bytecode and create swagger definition in `target/jaxrs-analyzer/swagger.json`.  
This definition can be imported into Postman or other tools for easy testing and can be used by clients to create client code.

The jaxrs-analyzer actually does some byte code analyzis and might be able to find out which entities you are
returning in your Response.   For more feature rich swagger generation and manual control you can look into 3rd party
generators like [Apiee](https://github.com/phillip-kruger/apiee/wiki) or use the latest Swagger 2.0 
[OpenApi 3.0 libraries](https://github.com/frantuma/swagger-core/wiki/Swagger-2.X---Getting-started). 



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

### Default Database
Every JEE7 server needs to have a default JDBC database resource available.  It is up to the container to decide which
database implementation this is, for Payara Micro 5 this is the H2 JDBC database (used to be Derby in earlier versions).

The default datasource can be found explicit in JNDI as `java:comp/DefaultDataSource` or more implicit using any of these :
```
@Resource(name="MyDataSource", lookup="java:comp/DefaultDataSource") DataSource myDS;
@Resource(name="MyDataSource") DataSource myDS;
@Resource DataSource myDS;
```

You can see how the default database connection works by looking into the `database` package and testing the resources below.  
```
curl -i http://localhost:8080/payaramicro/rest/database     <== returns datasource status message
curl -i http://localhost:8080/payaramicro/rest/database/2   <== returns name of person in databse with given id
```

### Default Database with EntityManager, Transactions and JPA
You can see how EntityManager works by looking into the `jpa` package and testing the resources below.  
The `persistence.xml` has been configured to use the default database connection,
create database by using metadata (annotations on entity) and populate the database
with a couple of rows using the `data.sql` script. 

The REST resource below will list all persons, fetch a specific person and add a new person to the database.
```
curl -i http://localhost:8080/payaramicro/rest/jpa
curl -i http://localhost:8080/payaramicro/rest/jpa/2
curl -i -X POST -H "Content-Type: application/json" -d '{"id":3,"name":"anne"}' http://localhost:8080/payaramicro/rest/jpa
```

### Custom JNDI databases directly in web.xml
With JEE7 it is possible to define JNDI data-source directly in web.xml and include the JDBC driver in the war.
This JDNI datasource can be injected anywhere in your CDI beans like this :
```
@Resource(mappedName="java:global/DataSource")
private DataSource dataSource;
```

The code in web.xml would look like this for Oracle using URL with ServiceName :
```
<data-source>
    <name>java:global/DataSource</name>
    <class-name>oracle.jdbc.pool.OracleDataSource</class-name>
    <user>username</user>
    <password>password</password>
    <property>
        <name>url</name>
        <value>jdbc:oracle:thin:@server.somewhere.no:1521/SERVICE.prod.system.local</value>
    </property>
</data-source>
``` 

For other database vendors we could use the simpler database-name syntax like MySQL :
```
<data-source>
    <name>java:global/DataSource</name>
    <class-name>com.mysql.jdbc.jdbc2.optional.MysqlDataSource</class-name>
    <server-name>server.somewhere.no</server-name>
    <port-number>3306</port-number>
    <database-name>databasename</database-name>
    <user>username</user>
    <password>password</password>
</data-source>
``` 



## MicroProfile 1.2 features
These features are yet to be included in the playground `Fault Tolerance 1.0`, `JWT Propagation 1.0`, 
`Health Metrics 1.0`, `Health Check 1.0`.

### Config API 1.1
By using the Config API it is possible to inject configuration directly into your code.
Default config override order is System Properties -> Environment Variables -> microprofile-config.properties

In the package `config` you'll find a small webservice that returns a config read from the properties file :
```
curl http://localhost:8080/payaramicro/rest/config
```



## JCache JSR-107
The JCache is only enabled when Payara Micro runs in cluster mode (which is default), take a look in the
`cache` package for an example.  It can be tested with the following curl commands :
```
curl http://localhost:8080/payaramicro/rest/cache/123
curl -X DELETE http://localhost:8080/payaramicro/rest/cache/123
curl -X DELETE http://localhost:8080/payaramicro/rest/cache/all
```



## Clustering
If you start multiple Payara Micro instanses they will automatically cluster together and share
session and cache data.  This feature is provided as default by [Hazelecast](https://hazelcast.org).