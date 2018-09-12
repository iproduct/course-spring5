package org.iproduct.spring.webflux.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import static org.iproduct.spring.webflux.model.User.*;
import static org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder;

import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
@Slf4j
public class SpringSecurityConfig {

    @Bean
    SecurityWebFilterChain configure(ServerHttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.GET, "/api/articles/**").permitAll()
                .pathMatchers(HttpMethod.DELETE, "/api/articles/**").hasRole("ADMIN")
                //.pathMatchers("/users/{user}/**").access(this::currentUserMatchesPath)
                .anyExchange().authenticated()
                .and()
                .httpBasic()
                .and()
                .build();

//                http
//                .csrf().disable()
//                    .authorizeExchange()
//                    .anyExchange().permitAll()
//                    .pathMatchers("/actuator/info").permitAll()
//                    .pathMatchers("/actuator/health").permitAll()
////                    .antMatchers("/v2/api-docs").permitAll()
////                    .antMatchers("/swagger*/**").permitAll()
//                    .pathMatchers(HttpMethod.GET, "/api/**").permitAll()
//                    .pathMatchers(HttpMethod.POST, "/**").permitAll()
////                    .pathMatchers(HttpMethod.POST, "/**").hasRole("ADMIN")
////                    .pathMatchers(HttpMethod.PUT).hasRole("USER")
////                    .pathMatchers(HttpMethod.PUT).hasRole("ADMIN")
////                    .pathMatchers(HttpMethod.DELETE).hasRole("USER")
////                    .pathMatchers(HttpMethod.DELETE).hasRole("ADMIN")
//                    .pathMatchers(HttpMethod.GET, "/**").permitAll()
//                .and()
//                    .formLogin()
//                .and()
//                    .httpBasic()
//                .and()
//                    .build();
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsRepository() {
        UserDetails user = User.withDefaultPasswordEncoder().username("user").password("user").roles("USER").build();
        UserDetails admin = User.withDefaultPasswordEncoder().username("admin").password("admin").roles("USER", "ADMIN").build();
        return new MapReactiveUserDetailsService(user, admin);
    }

//    public UserDetailsService userDetailsService() {
////        return username -> {
////            User found = users.getUserByUsername(username);
////            log.debug(">>> User authenticated for username: {} is: {}", username, found);
////            log.debug(">>> Auhorities: " + found.getAuthorities());
////            return found;
////        };
////    }
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(withDefaultPasswordEncoder()
//                .username("user").password("user").roles("USER").build());
//        return manager;
//    }
}