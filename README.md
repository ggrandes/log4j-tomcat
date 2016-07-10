# Log4J in Tomcat

Custom Appender for Log4j in Tomcat. Open Source Java project under Apache License v2.0

### Current Stable Version is [1.0.0](https://search.maven.org/#search|ga|1|g%3Aorg.javastack%20a%3Alog4j-tomcat)

---

## DOC

#### Usage Example

```properties
log4j.rootLogger=INFO, TOMCAT
# Define all the appenders
log4j.appender.TOMCAT=org.javastack.log4j.WebAppDailyRollingFileAppender
log4j.appender.TOMCAT.File=${catalina.base}/logs/tomcat.{context.name:default}.log
log4j.appender.TOMCAT.Append=true
log4j.appender.TOMCAT.Encoding=UTF-8
# Roll-over the log once per day
log4j.appender.TOMCAT.DatePattern='.'yyyy-MM-dd
log4j.appender.TOMCAT.layout=org.apache.log4j.PatternLayout
log4j.appender.TOMCAT.layout.ConversionPattern=%d{ISO8601} [%t] %p %c [%x] %m%n
```

---

## MAVEN

Add the dependency to your pom.xml:

    <dependency>
        <groupId>org.javastack</groupId>
        <artifactId>log4j-tomcat</artifactId>
        <version>1.0.0</version>
    </dependency>

---
Inspired in [log4j](https://logging.apache.org/log4j/1.2/apidocs/org/apache/log4j/DailyRollingFileAppender.html), this code is Java-minimalistic version.
