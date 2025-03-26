package gov.saip.applicationservice.config;

import gov.saip.applicationservice.filters.CustomAccessDeniedHandler;
import gov.saip.applicationservice.filters.CustomAuthenticationEntryPoint;
import gov.saip.applicationservice.filters.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private String[] PUBLIC_ENDPOINTS = {
            "/actuator/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/pb/**",
            "/internal-calling/**",
            "kc/**",
            "/kc/certificate-request/patent/**"
    };


    @Autowired
    public SecurityConfig() {
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    }


    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    protected JwtFilter authFilter() {
        return new JwtFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(PUBLIC_ENDPOINTS).permitAll()
                .anyRequest().authenticated();
        /*ADD FILTER AND HANDLE EXCEPTION*/
        http.addFilterBefore(authFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint()).accessDeniedHandler(new CustomAccessDeniedHandler())
        ;
        http.headers().frameOptions().disable();
        //TO ENABLE HT CONSOLE
    }
}

