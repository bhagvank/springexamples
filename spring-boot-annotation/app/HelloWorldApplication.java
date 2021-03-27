import org.springframework.context.annotation.*;

@SpringBootApplication
@EnableConfigServer
public class HelloWorldApplication {
   public static void main(String[] args) {
           SpringApplication.run(HelloWorldApplication.class, args);
   }
}

