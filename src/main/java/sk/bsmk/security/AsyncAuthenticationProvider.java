package sk.bsmk.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
public class AsyncAuthenticationProvider implements AuthenticationProvider {

  private static final Logger log = LoggerFactory.getLogger(AsyncAuthenticationProvider.class);
  
  private final AsyncRestTemplate restTemplate;

  @Autowired
  public AsyncAuthenticationProvider(AsyncRestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {

    ListenableFuture<ResponseEntity<String>> auth1Future = 
        restTemplate.getForEntity("http://localhost:8080/auth-1/" + authentication.getName(), String.class);

    ListenableFuture<ResponseEntity<String>> auth2Future =
        restTemplate.getForEntity("http://localhost:8080/auth-2/" + authentication.getName(), String.class);

    try {

      final String part1 = getWithTimeout(auth1Future);
      final String part2 = getWithTimeout(auth2Future);

      if ("OK".equals(part1) || "OK".equals(part2)) {
        final String name = part1 + "_" + part2;
        return new UsernamePasswordAuthenticationToken(name, "", null);
      } else {
        return null;
      }

    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }

  private static String getWithTimeout(ListenableFuture<ResponseEntity<String>> future) throws Exception {
    log.info("waiting started");
    try {
      // TODO it seems that both timeouts are important
      // one in Configuration and also on future. why?
      final HttpStatus status = future.get(1, TimeUnit.SECONDS).getStatusCode();
      return status == HttpStatus.OK ? "OK" : "NOK";
    } catch (TimeoutException e) {
      return "TIMEOUT";
    } finally {
      log.info("waiting ended");
    }
  }
  
}
