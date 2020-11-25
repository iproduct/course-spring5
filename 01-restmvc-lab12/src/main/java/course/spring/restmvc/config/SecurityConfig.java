package course.spring.restmvc.config;

import course.spring.restmvc.service.UserService;
import course.spring.restmvc.web.FilterChainExceptionHandlerFilter;
import course.spring.restmvc.web.JwtAuthenticationEntryPoint;
import course.spring.restmvc.web.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import static course.spring.restmvc.model.Role.ADMIN;
import static course.spring.restmvc.model.Role.AUTHOR;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpMethod.DELETE;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private FilterChainExceptionHandlerFilter filterChainExceptionHandlerFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
                .antMatchers(POST,"/api/login", "/api/register").permitAll()
                .antMatchers(GET,"/api/posts/**").permitAll()
                .antMatchers(POST, "/api/posts").hasAnyRole(AUTHOR.toString(), ADMIN.toString())
                .antMatchers(PUT, "/api/posts").hasAnyRole(AUTHOR.toString(), ADMIN.toString())
                .antMatchers(DELETE, "/api/posts").hasAnyRole(AUTHOR.toString(), ADMIN.toString())
                .antMatchers("/api/users/**").hasRole(ADMIN.toString())
                .antMatchers("/**").permitAll()
                .and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(filterChainExceptionHandlerFilter, LogoutFilter.class);
    }

    @Bean
    public UserDetailsService getUserDetailsService(UserService userService) {
        return userService::getUserByUsername;
    }

    @Bean
    public AuthenticationManager getAuthenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
