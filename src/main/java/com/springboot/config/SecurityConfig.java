package com.springboot.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }
//    @Bean
//    protected UserDetailsService users() {
//        UserDetails admin = User.builder()
//                .username("Admin")
//                .password(passwordEncoder().encode("Admin1234"))
//                .roles("ADMIN")
//                .build();
//            return new InMemoryUserDetailsManager(admin);
//
//    }
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers("/books/add/**").hasRole("ADMIN")
                .requestMatchers("/order/list/**").hasRole("ADMIN")
                .requestMatchers("/books/update/**").hasRole("ADMIN")
                .requestMatchers("/board/write/**").authenticated()
                .anyRequest().permitAll()
                )
        .formLogin(
            formLogin -> formLogin
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/loginfailed")
                .usernameParameter("username")
                .passwordParameter("password")

        )
                .logout(
                    logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                )

        ;
        return http.build();
    }


}
//사용자 역할별로 로그인 후 이동 경로를 다르게 지정
//.formLogin(formLogin -> formLogin
//        .loginPage("/login")
//        .loginProcessingUrl("/login")
//  ////      .successHandler((request, response, authentication) -> {
//        var authorities = authentication.getAuthorities();
//        String redirectUrl = "/";
//        for (var authority : authorities) {
//        if (authority.getAuthority().equals("ROLE_ADMIN")) {
//        redirectUrl = "/books/add";
//        break;
//        }
//        }
//        response.sendRedirect(redirectUrl);
//        })
//        .failureUrl("/loginfailed")
//        .usernameParameter("username")
//        .passwordParameter("password")
//        )
