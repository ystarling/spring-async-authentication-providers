package sk.bsmk.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sk.bsmk.Application;

import java.util.concurrent.TimeUnit;

@RestController
public class Controller {

  @RequestMapping(Application.AUTH_1 + "/{username}")
  public ResponseEntity<String> auth1(
      @PathVariable String username
  ) throws Exception {
    final String part = username.split("_")[0];
    return responseEntity(part);
  }

  @RequestMapping(Application.AUTH_2 + "/{username}")
  public ResponseEntity<String> auth2(
      @PathVariable String username
  ) throws Exception {
    final String part = username.split("_")[1];
    return responseEntity(part);
  }

  @RequestMapping(Application.CONTENT)
  public ResponseEntity<String> content() {
    final String principal = SecurityContextHolder.getContext().getAuthentication().getName();
    return new ResponseEntity<>(principal, HttpStatus.OK);
  }

  private static ResponseEntity<String> responseEntity(final String part) throws Exception {
    switch (part) {
      case "200":
        return new ResponseEntity<>(HttpStatus.OK);
      case "401":
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
      default: 
        Thread.sleep(TimeUnit.SECONDS.toMillis(3));
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
