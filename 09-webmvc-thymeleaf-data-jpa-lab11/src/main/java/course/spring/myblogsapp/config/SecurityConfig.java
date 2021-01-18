package course.spring.myblogsapp.config;

import course.spring.myblogsapp.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers(POST,"/login", "/register").permitAll()
                .antMatchers(POST, "/projects").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers(GET, "/projects").authenticated()
                .antMatchers("/").permitAll()
        .and()
        .formLogin();
    }

    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return userService::getUserByUsername;
    }

}
