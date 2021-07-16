package course.spring.blogs.config;

import course.spring.blogs.service.UserService;
import course.spring.blogs.web.JwtAuthenticationEntryPoint;
import course.spring.blogs.web.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static course.spring.blogs.entity.Role.ADMIN;
import static course.spring.blogs.entity.Role.AUTHOR;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
                .antMatchers(POST, "/api/login").permitAll()
                .antMatchers(POST, "/api/posts").hasAnyRole(AUTHOR.name(), ADMIN.name())
                .antMatchers(PUT, "/api/posts").hasAnyRole(AUTHOR.name(), ADMIN.name())
                .antMatchers(DELETE, "/api/posts").hasAnyRole(AUTHOR.name(), ADMIN.name())
                .antMatchers(GET, "/api/posts").permitAll()
                .antMatchers("/api/users").hasRole(ADMIN.name())
                .antMatchers("/**").permitAll()
            .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and()
                .sessionManagement().sessionCreationPolicy(STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return userService::getUserByUsername;
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
