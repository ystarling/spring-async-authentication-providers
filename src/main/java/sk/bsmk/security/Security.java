package sk.bsmk.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import sk.bsmk.Application;

@Configuration
@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter {

  @Autowired
  AsyncAuthenticationProvider asyncAuthenticationProvider;
  
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .httpBasic()
        .and()
        .authorizeRequests()
        .antMatchers(Application.AUTH_1 + "/**", Application.AUTH_2 + "/**")
        .permitAll()
        .antMatchers(Application.CONTENT)
        .authenticated();
    
    http.authenticationProvider(asyncAuthenticationProvider);
  }
  
}
