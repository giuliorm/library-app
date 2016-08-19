package library.config;

/**
 * Created by zotova on 04.08.2016.
 */
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;

@EnableWebSecurity
@ImportResource("classpath:applicationContext.xml")
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthenticationTokenProcessingFilter securityFilter;

    @Autowired
    CustomLdapUserDetailsService userDetailsService;

    @Autowired
    CustomAuthenticationProvider authenticationProvider;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider)
            .userDetailsService(userDetailsService);
             //   .inMemoryAuthentication()
              //  .withUser("user").password("password").roles("USER");
    }

    protected void configure(HttpSecurity http) throws Exception  {
                http
                     .sessionManagement()
                     .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/library/login/").permitAll()
                    .antMatchers("/library/main/*").hasRole("ADMIN")
                    .antMatchers("/library/test/*").hasRole("ADMIN")
                  .and()
                  .addFilterAfter(securityFilter, SecurityContextHolderAwareRequestFilter.class);
    }
}