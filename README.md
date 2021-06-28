# springexamples
spring examples

  
## Prerequisites

  * [Spring](https://repo.spring.io/release/org/springframework/spring/)

  * [Maven](https://maven.apache.org/download.cgi)

  
  


1.git clone this repository
```
git clone https://github.com/bhagvank/springexamples.git

``` 
2. In each spring example, you can build and run the tests using the commands below:
```
mvn package

mvn spring-boot:run

```

3. In each spring example, you can execute the target executable as :
```
java -jar target/spring-helloworld-0.1.0.jar

```
4. To execute the tests using maven
```
mvn test

```
5. To execute the java main class from the target :
```
mvn exec:java
```
6. To execute the java main class from the target :
```
mvn exec:java -Dexec.mainClass=com.journaldev.sparkdemo.WordCounter -Dexec.args=“input.txt"
```
