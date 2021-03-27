import org.spingframework.boot.SpringApplication;
import org.spingframework.boot.autoconfigure.SpringBootApplication;
import org.spingframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class HelloWorldApp {
   public static void main(String[] args) {
           SpringApplication.run(HelloWorldApplication.class, args);
   }
}
