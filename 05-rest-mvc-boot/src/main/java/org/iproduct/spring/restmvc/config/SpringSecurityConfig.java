package org.iproduct.spring.restmvc.config;

import lombok.extern.slf4j.Slf4j;
import org.iproduct.spring.restmvc.dao.UserRepository;
import org.iproduct.spring.restmvc.model.User;
import org.iproduct.spring.restmvc.security.RestAuthenticationEntryPoint;
import org.iproduct.spring.restmvc.security.RestSavedRequestAwareAuthenticationSuccessHandler;
import org.iproduct.spring.restmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
@Slf4j
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private RestSavedRequestAwareAuthenticationSuccessHandler authenticationSuccessHandler;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                    .authorizeRequests()
                    .antMatchers("/login").permitAll()
                    .antMatchers(HttpMethod.GET,"/..").authenticated()
//                    .antMatchers("/..").permitAll()
////                  .antMatchers(HttpMethod.POST, "/**").hasAnyRole("USER", "ADMIN")
//                    .antMatchers(HttpMethod.PUT).hasAnyRole("USER", "ADMIN")
//                    .antMatchers(HttpMethod.DELETE).hasAnyRole("USER", "ADMIN")
                .and()
                    .formLogin()
                    .successHandler(authenticationSuccessHandler)
                    .failureHandler(new SimpleUrlAuthenticationFailureHandler())
                .and()
                    .logout();
//        http.csrf().disable()
//                .authorizeRequests()
////                    .antMatchers(HttpMethod.GET, "/**").hasAnyRole("USER", "ADMIN")
//                    .antMatchers(HttpMethod.POST, "/**").hasAnyRole("USER", "ADMIN")
//                    .antMatchers(HttpMethod.PUT).hasAnyRole("USER", "ADMIN")
//                    .antMatchers(HttpMethod.DELETE).hasAnyRole("USER", "ADMIN")
//                    .antMatchers("/**").permitAll()
//                .and()
//                    .formLogin()
//                .and()
//                    .httpBasic();
    }

    @Bean
    public UserDetailsService userDetailsService(UserService users) {
        return username -> {
            User found = users.getUserByUsername(username);
            log.debug(">>> User authenticated for username: {} is: {}", username, found);
            log.debug(">>> Auhorities: "+ found.getAuthorities());
            return found;
        };
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {

        auth.inMemoryAuthentication()
                .withUser("temporary").password("temporary").roles("ADMIN")
                .and()
                .withUser("user").password("userPass").roles("USER");
    }

    @Bean
    public RestSavedRequestAwareAuthenticationSuccessHandler mySuccessHandler(){
        return new RestSavedRequestAwareAuthenticationSuccessHandler();
    }
    @Bean
    public SimpleUrlAuthenticationFailureHandler myFailureHandler(){
        return new SimpleUrlAuthenticationFailureHandler();
    }

//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withDefaultPasswordEncoder()
//                .username("user").password("user").roles("USER").build());
//        return manager;
}