package ous.LabraryWebSite.Security;

import jakarta.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ous.LabraryWebSite.Component.OAuth2LoginSuccessHandler;
import ous.LabraryWebSite.Component.SessionInvalidationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomUserDetailService userDetailsService;
    private final OAuth2LoginSuccessHandler loginSuccessHandler;

    public SecurityConfig(CustomUserDetailService userDetailsService , OAuth2LoginSuccessHandler loginSuccessHandler) {
        this.userDetailsService = userDetailsService;
        this.loginSuccessHandler = loginSuccessHandler;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, SessionInvalidationFilter loginPageSessionInvalidationFilter) throws Exception {
        http
                .addFilterBefore(loginPageSessionInvalidationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorize-> authorize
                        .requestMatchers("/login" ,"/register","/register/save", "/book" , "/css/**" , "/js/**")
                        .permitAll()
                        .requestMatchers("/book/add")
                        .hasAuthority("ADMIN")
                        .anyRequest()
                        .authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login") // Custom login page
                        .successHandler(loginSuccessHandler)
                        .failureUrl("/login?error=true")
                )
                .formLogin( form-> form
                        .loginPage("/book")
                        .defaultSuccessUrl("/book" , true)
                        .loginProcessingUrl("/login")
                        .failureUrl("/login?error=true")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll()
                );
        return http.build();
    }
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    
}
