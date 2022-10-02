package security;
import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class HelloWorldApp {
    
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(HelloWorldApp.class, args);
        
        System.out.println("Inspecting the beans");
        
        String[] beans = ctx.getBeanDefinitionNames();
        Arrays.sort(beans);
        for (String name : beans) {
            System.out.println("Bean Name" +name);
        }
    }

}
