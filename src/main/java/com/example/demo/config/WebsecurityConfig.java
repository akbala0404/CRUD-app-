package com.example.demo.config;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
@Configuration
public class WebsecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public UserDetailsService userDetailsService() {
        return super.userDetailsService();
    }
    @Autowired
    UserDetailsService userDetailsService;
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

   @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/style/", "/scripts/", "/img/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST).hasAnyRole("ADMIN","MANAGER")
                .antMatchers(HttpMethod.PUT).hasAnyRole("ADMIN","MANAGER")
                .antMatchers(HttpMethod.DELETE).hasAnyRole("MANAGER")
                .antMatchers(HttpMethod.GET,"/courses").hasAnyRole("ADMIN","MANAGER","USER")
                .antMatchers(HttpMethod.POST,"/users/addCourse").hasAnyRole("ADMIN","MANAGER","USER")
                .antMatchers(HttpMethod.GET,"/users").hasAnyRole("ADMIN","MANAGER")
                .antMatchers(HttpMethod.GET,"/users/{userId}").access("@userSecurity.hasUserId(authentication,#userId)")
        ;
        http.cors().disable();
        http.csrf().disable();
        http.headers().frameOptions().disable();
        super.configure(http);
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/h2-console/**");
    }

}