package course.spring.restjpa.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@Slf4j
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(GET, "/api/users").hasAnyRole("ADMIN")
                .antMatchers(POST, "/**").hasAnyRole("USER", "ADMIN")
                .antMatchers(PUT, "/**").hasAnyRole("USER", "ADMIN")
                .antMatchers(DELETE, "/**").hasRole("ADMIN")
                .antMatchers(GET, "/**").permitAll()
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }

//    @Bean
//    public UserDetailsService userDetailsService(UserService users) {
//        return username -> {
//            try {
//                UserDetails found = users.getUserByUsername(username);
//                log.debug(">>> User authenticated for username: {} is {}", username, found);
//                return found;
//            } catch (EntityNotFoundException ex) {
//                throw new UsernameNotFoundException(ex.getMessage(), ex);
//            }
//        };
//    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withDefaultPasswordEncoder()
                .username("user").password("user")
                .roles("ADMIN").build());
        return manager;
    }

//    @Bean
//    public BasicAuthenticationEntryPoint swaggerAuthenticationEntryPoint() {
//        BasicAuthenticationEntryPoint entryPoint = new BasicAuthenticationEntryPoint();
//        entryPoint.setRealmName("Swagger Realm");
//        return entryPoint;
//    }
}
