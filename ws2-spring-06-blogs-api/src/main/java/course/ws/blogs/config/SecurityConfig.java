package course.ws.blogs.config;

import course.ws.blogs.entity.Role;
import course.ws.blogs.exception.EntityNotFoundException;
import course.ws.blogs.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
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
                .mvcMatchers(GET, "/api/articles","/api/articles/**").permitAll()
                .mvcMatchers(GET, "/api/users","/api/users/**").hasRole(ADMIN.name())
                .mvcMatchers("/**").hasAnyRole(ADMIN.name(), AUTHOR.name())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    UserDetailsService userDetailsService(UserService userService){
        return email -> {
            try {
                var user = userService.findUserByEmail(email);
                return user;
            } catch(EntityNotFoundException ex) {
                throw new UsernameNotFoundException(ex.getMessage());
            }
        };
    }
}











