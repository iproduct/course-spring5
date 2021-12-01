package course.spring.webmvc.config;

import course.spring.webmvc.domain.UsersService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
//            .antMatchers(POST,"/login", "/register").permitAll()
            .antMatchers().permitAll()
            .antMatchers("/").permitAll()
        .and()
        .formLogin();
    }

    @Bean
    public UserDetailsService userDetailsService(UsersService userService) {
        return userService::findByUsername;
    }

}
