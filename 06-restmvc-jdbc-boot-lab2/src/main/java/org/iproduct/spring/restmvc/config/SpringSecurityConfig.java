package org.iproduct.spring.restmvc.config;

import lombok.extern.slf4j.Slf4j;
import org.iproduct.spring.restmvc.exception.EntityNotFoundException;
import org.iproduct.spring.restmvc.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@Slf4j
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/**").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/**").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")
                .antMatchers("/**").permitAll()
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().passwordEncoder().withUser("user").password("user").roles("USER");
//    }

    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return username -> {
            try {
                UserDetails found = userService.getUserByUsername(username);
                log.info(">>> User authenticated: {}", found);
                return found;
            } catch(EntityNotFoundException ex) {
                throw new UsernameNotFoundException(ex.getMessage());
            }
        };
    }

}
