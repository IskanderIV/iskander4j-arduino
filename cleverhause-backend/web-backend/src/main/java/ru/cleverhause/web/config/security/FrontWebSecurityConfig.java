package ru.cleverhause.web.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.session.SimpleRedirectInvalidSessionStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = {"ru.cleverhause.common.api.service.security"})
public class FrontWebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String SITE = "/";
    private static final String ALL_INSIDE = "/**";

    @Bean
    public BasicAuthenticationEntryPoint frontAuthEntryPoint() {
        BasicAuthenticationEntryPoint entryPoint = new BasicAuthenticationEntryPoint();
        entryPoint.setRealmName("Front_REST_Realm");

        return entryPoint;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/myboard/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/home").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/registration").permitAll()
                .and()
                // if role is not correct than we fall down here
                .exceptionHandling().accessDeniedPage("/error/permissionError")
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/home")
                .failureUrl("/login?error")
                .usernameParameter("username")
                .passwordParameter("password")
                //                .successHandler(authSuccessHandler)
                //                .failureHandler(authFailureHandler)
                .and()
                .logout()
                //                .logoutSuccessHandler(logoutSuccessHandler)
                .logoutUrl("/login?logout")
                .invalidateHttpSession(true)
//                .deleteCookies(COOKIES_JSESSIONID)
                .logoutRequestMatcher(new AntPathRequestMatcher("/login?logout", HttpMethod.POST.name()));

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .invalidSessionStrategy(new SimpleRedirectInvalidSessionStrategy("/home"))
                .sessionAuthenticationErrorUrl("/home")
                .maximumSessions(1);
    }
}