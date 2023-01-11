package course.spring.blogs.config;

import course.spring.blogs.exception.NonexistingEntityException;
import course.spring.blogs.service.UserService;
import course.spring.blogs.web.jwt.JwtAuthenticationEntryPoint;
import course.spring.blogs.web.jwt.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import static course.spring.blogs.entity.Role.*;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig {
    @Autowired
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .mvcMatchers(POST, "/api/auth/login","/api/auth/register").permitAll()
                .mvcMatchers(GET, "/swagger-ui", "/swagger-ui/**", "/swagger-resources/**", "/v2/**").permitAll()
                .mvcMatchers(GET, "/api/articles").permitAll()
                .mvcMatchers(GET, "/api/users", "api/users/**").authenticated() //.hasRole(ADMIN.name())
                .mvcMatchers("/api/users", "api/users/**").hasRole(ADMIN.name())
                .mvcMatchers("/**").hasAnyRole(ADMIN.name(), AUTHOR.name(), READER.name())
//                .mvcMatchers(GET,"/**").hasAnyRole(ADMIN.name(), AUTHOR.name(), READER.name())
//                .mvcMatchers(POST, "/**").hasAnyRole(ADMIN.name(), AUTHOR.name())
//                .mvcMatchers(PUT, "/**").hasAnyRole(ADMIN.name(), AUTHOR.name())
//                .mvcMatchers(DELETE, "/**").hasAnyRole(ADMIN.name(), AUTHOR.name())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http, UserDetailsService userDetailsService)
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder())
                .and()
                .build();
    }

    @Bean
    public HttpFirewall getHttpFirewall() {
        StrictHttpFirewall strictHttpFirewall = new StrictHttpFirewall();
        strictHttpFirewall.setAllowSemicolon(true);
        return strictHttpFirewall;
    }

    @Bean
    public WebSecurityCustomizer ignoringCustomizer() {
        return (web) -> web.ignoring().antMatchers("/**");
    }

    @Bean
    UserDetailsService userDetailsService(UserService userService){
        return (String username) -> {
            try {
                return userService.getUserByUsername(username);
            } catch (NonexistingEntityException ex) {
                throw new UsernameNotFoundException(ex.getMessage());
            }
        };
    }
}
