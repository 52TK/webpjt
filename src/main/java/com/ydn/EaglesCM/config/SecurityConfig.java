package com.ydn.EaglesCM.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/css/**", "/js/**","/img/**","/error/**","/lib/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests( authorize -> authorize
                        // URL 에 따른 권한 설정
                        .mvcMatchers(
                                "/members/join",
                                "/members/login"
                                ,"members/check/**"
                        )
                        .anonymous()
                        .mvcMatchers(
                                "/articles/**",
                                "/",
                                "/members/modify/**"

                        )
                        .permitAll()
                                .mvcMatchers(
                                        "/boards/**"
                                        , "/mypage/**"
                                ).hasAnyRole("MEMBER", "ADMIN")
                .mvcMatchers(
                        "/adm/**"
                )
                .hasRole("ADMIN")
                .anyRequest()
                .denyAll()
                )

                .formLogin()
                    .loginPage("/members/login")
                    .loginProcessingUrl("/members/doLogin")
                    .usernameParameter("loginId")
                    .passwordParameter("loginPw")
                    .defaultSuccessUrl("/")

                .and()

                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .clearAuthentication(true)

                .and()

                    .sessionManagement()
                        .invalidSessionUrl("/")
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true)
                        .expiredUrl("/");

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
