package course.spring.restmvc.config;

import course.spring.restmvc.model.entity.User;
import course.spring.restmvc.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .httpBasic(withDefaults())
                .authorizeRequests()
                    .antMatchers("/api/users/**").hasRole("ADMIN")
                    .antMatchers("/actuator/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/posts/**").authenticated()
                    .antMatchers(HttpMethod.POST, "/api/posts").hasAnyRole("AUTHOR", "ADMIN")
                    .antMatchers(HttpMethod.PUT, "/api/posts/**").hasAnyRole("AUTHOR", "ADMIN")
                    .antMatchers(HttpMethod.DELETE).hasRole("ADMIN")

                .and()
                    .formLogin()
                .and()
                    .logout();
    }

    @Bean
    UserDetailsService userDetailsService(UserService userService) {
        return username -> userService.getUserByUsername(username);
    }
}
