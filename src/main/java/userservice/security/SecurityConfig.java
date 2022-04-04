package userservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationExceptionHandler authenticationExceptionHandler;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService, AuthenticationExceptionHandler authenticationExceptionHandler) {
        this.userDetailsService = userDetailsService;
        this.authenticationExceptionHandler = authenticationExceptionHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .exceptionHandling().authenticationEntryPoint(authenticationExceptionHandler).and()
                .authorizeRequests()
                .mvcMatchers(HttpMethod.GET,"/api/users").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.PATCH,"/api/users/{id}/addRole", "/api/users/{id}/removeRole").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.DELETE,"/api/users/{id}").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.PUT,"/api/users/{id}").hasAnyRole("ADMIN","USER")
                .mvcMatchers(HttpMethod.POST,"/api/users").permitAll()
                .anyRequest().authenticated().and().httpBasic();

    }
    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
}
