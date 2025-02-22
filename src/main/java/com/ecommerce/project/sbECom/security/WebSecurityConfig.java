package com.ecommerce.project.sbECom.security;

import com.ecommerce.project.sbECom.model.AppRole;
import com.ecommerce.project.sbECom.model.Role;
import com.ecommerce.project.sbECom.model.User;
import com.ecommerce.project.sbECom.repositories.RoleRepository;
import com.ecommerce.project.sbECom.repositories.UserRepository;
import com.ecommerce.project.sbECom.security.jwt.AuthEntryPointJwt;
import com.ecommerce.project.sbECom.security.jwt.AuthTokenFilter;
import com.ecommerce.project.sbECom.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;
import java.util.Set;

//Sets up the security filter chain, permitting or denying access based on paths and roles.
// it also configures session management to be stateless, which is crucial for JWT usage.
@Configuration
@EnableWebSecurity
@EnableMethodSecurity //enables role based authorization
public class WebSecurityConfig {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unAuthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter(){
        return new AuthTokenFilter();
    }
    //cuz we want to make use of our custom User detailsService && password
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    SecurityFilterChain FilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) //for h2 console
                .exceptionHandling(e -> e.authenticationEntryPoint(
                        unAuthorizedHandler
                ))
                .sessionManagement(session -> session.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS //to make the stateless API(no cookies)
                ))
                .authorizeHttpRequests((requests) -> (
                        requests.requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/v3/api-docs/**").permitAll()
                                .requestMatchers("/h2-console/**").permitAll()
                                //.requestMatchers("/api/admin/**").permitAll()
                                .requestMatchers("/api/public/**").permitAll()
                                .requestMatchers("/swagger-ui/**").permitAll()
                                .requestMatchers("/api/test/**").permitAll()
                                .requestMatchers("/images/**").permitAll()
                            .anyRequest()).authenticated());
//       // http.formLogin(Customizer.withDefaults());
//        http.httpBasic(Customizer.withDefaults());
//        //allows frames from only same origin ,for h2 console
        http.headers(headers ->
                headers.frameOptions(frames -> frames.sameOrigin()));

        http.authenticationProvider(authenticationProvider());
        //here we are telling SB to execute our custom filter before its built in filter
        http.addFilterBefore(authenticationJwtTokenFilter() , UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }
    //any path with these pattern will be excluded from filter chain
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web -> web.ignoring().requestMatchers(
                "/v3/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**"
        ));
    }
    //custom password encoder
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
