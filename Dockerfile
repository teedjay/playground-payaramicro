FROM maven:3.5-jdk-8 as MAVENBUILDER
COPY src /usr/src/payaramicro/src
COPY pom.xml /usr/src/payaramicro
RUN mvn -f /usr/src/payaramicro/pom.xml clean package -Dmaven.test.skip=true

FROM payara/micro:5.182
COPY --from=MAVENBUILDER /usr/src/payaramicro/target/*.war /opt/payara/deployments

# docker build -t my_payara_image .
# docker run -p 8080:8080 -d --name playgound_payara my_payara_image
