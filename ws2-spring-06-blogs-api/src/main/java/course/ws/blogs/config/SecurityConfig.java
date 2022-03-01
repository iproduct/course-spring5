package course.ws.blogs.config;

import course.ws.blogs.entity.Role;
import course.ws.blogs.exception.EntityNotFoundException;
import course.ws.blogs.service.UserService;
import course.ws.blogs.web.jwt.JwtAuthenticationEntryPoint;
import course.ws.blogs.web.jwt.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static course.ws.blogs.entity.Role.ADMIN;
import static course.ws.blogs.entity.Role.AUTHOR;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .mvcMatchers(POST, "/api/auth/login","/api/auth/register").permitAll()
                .mvcMatchers(GET, "/api/articles","/api/articles/**").permitAll()
                .mvcMatchers(GET, "/api/users","/api/users/**").hasRole(ADMIN.name())
                .mvcMatchers("/**").hasAnyRole(ADMIN.name(), AUTHOR.name())
                .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
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











