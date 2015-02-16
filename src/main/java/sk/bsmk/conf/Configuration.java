package sk.bsmk.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.http.client.AsyncClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@org.springframework.context.annotation.Configuration
public class Configuration {
  
  @Bean
  AsyncRestTemplate restTemplate() {
    HttpComponentsAsyncClientHttpRequestFactory factory = new HttpComponentsAsyncClientHttpRequestFactory();
    
    factory.setConnectionRequestTimeout(1000);

    final AsyncRestTemplate restTemplate = new AsyncRestTemplate(factory);
    restTemplate.setErrorHandler(new ResponseErrorHandler() {

      @Override
      public boolean hasError(ClientHttpResponse response) throws IOException {
        return false;
      }

      @Override
      public void handleError(ClientHttpResponse response) throws IOException {

      }
    });
    return restTemplate;
  }
  
}
