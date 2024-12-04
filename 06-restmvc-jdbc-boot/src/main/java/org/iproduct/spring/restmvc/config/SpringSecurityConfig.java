package org.iproduct.spring.restmvc.config;

import lombok.extern.slf4j.Slf4j;
import org.iproduct.spring.restmvc.exception.EntityNotFoundException;
import org.iproduct.spring.restmvc.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;

import static org.iproduct.spring.restmvc.model.Role.ADMIN;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
@Slf4j
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorizeHttpRequests ->
                                authorizeHttpRequests
                                        .requestMatchers(POST, "/api/auth/login", "/api/auth/register").permitAll()
                                        .requestMatchers(GET, "/swagger-ui.html", "/swagger-ui/**", "/swagger-resources/**", "/v2/**").permitAll()
                                        .requestMatchers(GET, "/api/articles").permitAll()
                                        .requestMatchers(GET, "/api/users", "api/users/**").authenticated() //.hasRole(ADMIN.name())
                                        .requestMatchers("/api/users", "api/users/**").hasRole(ADMIN.name())
                                        .requestMatchers("/**").permitAll()
                        //hasAnyRole(ADMIN.name(), AUTHOR.name(), READER.name())
//                                .requestMatchers(GET, "/**").hasAnyRole(ADMIN.name(), AUTHOR.name(), READER.name())
//                                .requestMatchers(POST, "/**").hasAnyRole(ADMIN.name(), AUTHOR.name())
//                                .requestMatchers(PUT, "/**").hasAnyRole(ADMIN.name(), AUTHOR.name())
//                                .requestMatchers(DELETE, "/**").hasAnyRole(ADMIN.name(), AUTHOR.name())
                ).formLogin(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserService users) {
        return username -> {
            try {
                UserDetails found = users.getUserByUsername(username);
                log.debug(">>> User authenticated for username: {} is {}", username, found);
                return found;
            } catch (EntityNotFoundException ex) {
                throw new UsernameNotFoundException(ex.getMessage(), ex);
            }
        };
    }

//        @Bean
//        public UserDetailsService userDetailsService () {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withDefaultPasswordEncoder()
//                .username("user").password("user")
//                .roles("USER").build());
//        return manager;
//    }

    }
