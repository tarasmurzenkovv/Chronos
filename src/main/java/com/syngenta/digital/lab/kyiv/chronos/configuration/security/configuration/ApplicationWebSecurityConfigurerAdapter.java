package com.syngenta.digital.lab.kyiv.chronos.configuration.security.configuration;

import com.syngenta.digital.lab.kyiv.chronos.configuration.security.jwt.ApplicationAuthenticationEntryPoint;
import com.syngenta.digital.lab.kyiv.chronos.configuration.security.service.JwtAuthenticationFilter;
import com.syngenta.digital.lab.kyiv.chronos.configuration.security.service.ApplicationUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
@RequiredArgsConstructor
public class ApplicationWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
    private final ApplicationUserDetailsService applicationUserDetailsService;
    private final ApplicationAuthenticationEntryPoint unauthorizedHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(applicationUserDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable();

        publicResources(http);


        http.exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler);

        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .authorizeRequests().anyRequest().authenticated();

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    private void publicResources(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/api/v2/api-docs").permitAll();
        http.authorizeRequests().antMatchers("/swagger-ui.html").permitAll();
        http.authorizeRequests().antMatchers("/webjars/springfox-swagger-ui/**").permitAll();
        http.authorizeRequests().antMatchers("/configuration/ui").permitAll();
        http.authorizeRequests().antMatchers("/swagger-resources").permitAll();
        http.authorizeRequests().antMatchers("/v2/api-docs").permitAll();
        http.authorizeRequests().antMatchers("/",
                "/**/.ico",
                "/**/*.json",
                "/**/*.png",
                "/**/*.gif",
                "/**/*.svg",
                "/**/*.jpg",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js",
                "/api/v0/user/login",
                "/api/v0/user").permitAll();
    }
}
