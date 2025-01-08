package course.spring.blog.config;

import course.spring.blog.exception.NonexistingEntityException;
import course.spring.blog.service.UserService;
import jakarta.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.formLogin(formLogin ->
                formLogin
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .loginPage("/login")
        ).logout(logout ->
                logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
        ).authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests
                                .requestMatchers("/posts", "/posts/**").authenticated()
                                .anyRequest().permitAll()
//                                .anyRequest().permitAll()
//                                        .requestMatchers(POST, "/api/auth/login", "/api/auth/register").permitAll()
//                                        .requestMatchers(GET, "/swagger-ui.html", "/swagger-ui/**", "/swagger-resources/**", "/v2/**").permitAll()
//                                        .requestMatchers(GET, "/api/articles").permitAll()
//                                        .requestMatchers(GET, "/api/users", "api/users/**").authenticated() //.hasRole(ADMIN.name())
//                                        .requestMatchers("/api/users", "api/users/**").hasRole(ADMIN.name())
//                                        .requestMatchers("/**").permitAll() //hasAnyRole(ADMIN.name(), AUTHOR.name(), READER.name())
//                                .requestMatchers(GET, "/**").hasAnyRole(ADMIN.name(), AUTHOR.name(), READER.name())
//                                .requestMatchers(POST, "/**").hasAnyRole(ADMIN.name(), AUTHOR.name())
//                                .requestMatchers(PUT, "/**").hasAnyRole(ADMIN.name(), AUTHOR.name())
//                                .requestMatchers(DELETE, "/**").hasAnyRole(ADMIN.name(), AUTHOR.name());
//                ).formLogin(withDefaults());
        );
        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http, UserDetailsService userDetailsService)
            throws Exception {
        var authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
        return authManagerBuilder.build();
    }

    @Bean
    UserDetailsService userDetailsService(UserService userService) {
        return (String username) -> {
            try {
                return userService.getUserByUsername(username);
            } catch (NonexistingEntityException ex) {
                throw new UsernameNotFoundException(ex.getMessage());
            }
        };
    }
}
