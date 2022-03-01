package course.ws.blogs.config;

import course.ws.blogs.exception.EntityNotFoundException;
import course.ws.blogs.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static course.ws.blogs.entity.Role.ADMIN;
import static course.ws.blogs.entity.Role.AUTHOR;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .mvcMatchers(GET, "/api/articles", "/api/articles/**").permitAll()
                .mvcMatchers(POST, "/api/articles").hasAnyRole(AUTHOR.name(), ADMIN.name())
                .mvcMatchers("/api/users","/api/users/**").hasRole(ADMIN.name());

    }

    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return username -> {
            try {
                return userService.getByUsername(username);
            } catch (EntityNotFoundException ex) {
                throw new UsernameNotFoundException(ex.getMessage());
            }
        };
    }

}












