package course.spring.webmvc.config;
import course.spring.webmvc.domain.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
//
//    @Autowired
//    private RestSavedRequestAwareAuthenticationSuccessHandler authenticationSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
//                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
                .antMatchers("/actuator/info").permitAll()
                .antMatchers("/actuator/health").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/swagger*/**").permitAll()
                .antMatchers(HttpMethod.GET, "/articles/**", "/user/**").authenticated()
                .antMatchers(HttpMethod.POST, "/**").hasAnyRole("AUTHOR", "ADMIN")
                .antMatchers(HttpMethod.PUT).hasAnyRole("AUTHOR", "ADMIN")
                .antMatchers(HttpMethod.DELETE).hasAnyRole("AUTHOR", "ADMIN")
                .and()
                .formLogin()
                .permitAll()
//                .successHandler(authenticationSuccessHandler)
//                .failureHandler(new SimpleUrlAuthenticationFailureHandler())
                .and()
                .logout()
                    .deleteCookies("JSESSIONID")
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .logoutUrl("/logout");
//                .and()
//                    .rememberMe();
    }

    @Bean
    public UserDetailsService userDetailsService(UsersService usersService) {

        return username -> {
            try {
                return usersService.findByUsername(username);
            } catch (course.spring.restmvc.exception.NonexisitngEntityException ex) {
                throw new UsernameNotFoundException(ex.getMessage(), ex);
            }
        };

    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withDefaultPasswordEncoder()
//                .username("user").password("user")
//                .roles("USER").build());
//        return manager;
//    }

}
