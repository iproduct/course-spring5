package org.iproduct.spring.webmvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .requestMatchers()
                    .antMatchers("/**")
                .and()
                    .authorizeRequests()
                        .antMatchers(HttpMethod.POST).hasRole("USER")
                        .antMatchers(HttpMethod.PUT).hasRole("USER")
                        .antMatchers(HttpMethod.DELETE).hasRole("USER")
                        .antMatchers("/**").permitAll()
                .and()
                    .formLogin()
                .and()
                    .httpBasic();

    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withDefaultPasswordEncoder()
                .username("user").password("user").roles("USER").build());
        return manager;
    }
}