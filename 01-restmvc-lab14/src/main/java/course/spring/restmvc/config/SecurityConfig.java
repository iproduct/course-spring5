package course.spring.restmvc.config;

import course.spring.restmvc.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.springframework.http.HttpMethod.*;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(POST, "/api/login").permitAll()
                .antMatchers(GET, "/api/**").permitAll()
                .antMatchers(POST, "/api/**").authenticated()
                .antMatchers(PUT, "/api/**").authenticated()
                .antMatchers(DELETE, "/api/**").authenticated()
                .antMatchers("**").permitAll()
//                .and()
//                    .httpBasic()
                .and()
                    .logout();
    }

    @Bean
    UserDetailsService userDetailsService(UserService userService) {
        return userService::getUserByUsername;
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
