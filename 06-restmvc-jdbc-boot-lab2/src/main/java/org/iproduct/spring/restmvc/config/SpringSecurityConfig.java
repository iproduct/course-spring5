package org.iproduct.spring.restmvc.config;

import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableWebSecurity
public class SpringSecurityConfig {
//    extends WebSecurityConfigurerAdapter {

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/**").hasRole("USER")
//                .antMatchers(HttpMethod.PUT, "/**").hasRole("USER")
//                .antMatchers(HttpMethod.DELETE, "/**").hasRole("USER")
//                .antMatchers("/**").permitAll()
//            .and()
//                .formLogin()
//            .and()
//                .httpBasic();
//    }
//
////    @Override
////    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////        auth.inMemoryAuthentication().passwordEncoder().withUser("user").password("user").roles("USER");
////    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withDefaultPasswordEncoder()
//            .username("user").password("user")
//            .roles("USER").build());
//        return manager;
//    }

}
