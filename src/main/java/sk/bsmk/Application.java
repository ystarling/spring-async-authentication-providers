package sk.bsmk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@EnableAutoConfiguration
@ComponentScan
public class Application {

  public static final String AUTH_1 = "/auth-1";
  public static final String AUTH_2 = "/auth-2";
  public static final String CONTENT = "/content";
  
  public static void main(String ... args) {
    SpringApplication.run(Application.class);
  }

}
