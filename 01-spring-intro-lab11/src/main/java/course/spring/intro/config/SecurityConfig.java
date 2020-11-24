package course.spring.intro.config;

import course.spring.intro.service.UserService;
import course.spring.intro.web.JwtAuthenticationEntryPoint;
import course.spring.intro.web.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static course.spring.intro.model.Role.ADMIN;
import static course.spring.intro.model.Role.AUTHOR;
import static org.springframework.http.HttpMethod.*;

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
            .antMatchers(POST,"/api/login", "/api/register").permitAll()
            .antMatchers(GET,"/api/posts/**").permitAll()
            .antMatchers(POST, "/api/posts").hasAnyRole(AUTHOR.toString(), ADMIN.toString())
            .antMatchers(PUT, "/api/posts").hasAnyRole(AUTHOR.toString(), ADMIN.toString())
            .antMatchers(DELETE, "/api/posts").hasAnyRole(AUTHOR.toString(), ADMIN.toString())
            .antMatchers("/api/users/**").hasRole(ADMIN.toString())
            .antMatchers("/**").permitAll()
            .and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return userService::getUserByUsername;
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
