package sk.bsmk;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ApplicationTest {

  private static final String CONTENT = "http://localhost:8080/content";
  
  @Test
  public void that_200_200_gets_OK_OK() {
    RestTemplate restTemplate = new TestRestTemplate("200_200", "");
    ResponseEntity<String> response = restTemplate.getForEntity(CONTENT, String.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("OK_OK", response.getBody());
  }

  @Test
  public void that_200_401_gets_OK_NOK() {
    RestTemplate restTemplate = new TestRestTemplate("200_401", "");
    ResponseEntity<String> response = restTemplate.getForEntity(CONTENT, String.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("OK_NOK", response.getBody());
  }

  @Test
  public void that_401_200_gets_NOK_OK() {
    RestTemplate restTemplate = new TestRestTemplate("401_200", "");
    ResponseEntity<String> response = restTemplate.getForEntity(CONTENT, String.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("NOK_OK", response.getBody());
  }

  @Test
  public void that_401_401_gets_401() {
    RestTemplate restTemplate = new TestRestTemplate("401_401", "");
    ResponseEntity<String> response = restTemplate.getForEntity(CONTENT, String.class);
    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
  }

  @Test
  public void that_NONE_200_gets_TIMEOUT_OK() {
    RestTemplate restTemplate = new TestRestTemplate("NONE_200", "");
    ResponseEntity<String> response = restTemplate.getForEntity(CONTENT, String.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("TIMEOUT_OK", response.getBody());
  }

  @Test
  public void that_200_NONE_gets_OK_TIMEOUT() {
    RestTemplate restTemplate = new TestRestTemplate("200_NONE", "");
    ResponseEntity<String> response = restTemplate.getForEntity(CONTENT, String.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("OK_TIMEOUT", response.getBody());
  }

  @Test
  public void that_NONE_NONE_gets_401() {
    RestTemplate restTemplate = new TestRestTemplate("NONE_NONE", "");
    ResponseEntity<String> response = restTemplate.getForEntity(CONTENT, String.class);
    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
  }
  
}