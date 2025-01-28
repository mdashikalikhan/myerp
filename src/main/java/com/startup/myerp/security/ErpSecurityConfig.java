package com.startup.myerp.security;

import com.startup.myerp.service.ErpUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Configuration
@EnableWebSecurity
public class ErpSecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public UserDetailsService userDetailsService(ErpUserDetailService erpUserDetailService) {
        return erpUserDetailService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(
                    auth->auth
                            .requestMatchers("/admin/**").hasRole("ADMIN")
                            .requestMatchers("/user/**").hasAnyRole("USER", "CLIENT")
                            .requestMatchers("/", "/register", "/login", "/h2", "/js/**" ,"/h2/**").permitAll()
                            .anyRequest().authenticated()
                )
                .formLogin(
                    login->login
                            .loginPage("/login")
                            .successHandler(erpSuccessHandler())
                            .permitAll()
                )
                .logout(
                    logout->logout
                            .invalidateHttpSession(true)
                            .logoutUrl("/logout")
                            .logoutSuccessUrl("/")

                            /*.logoutSuccessHandler(
                                    (request, response, authentication)->{
                                        request.getSession(true).setAttribute("logout", "logout");

                                        request.getRequestDispatcher("/").forward(request,
                                                response);
                                    }
                            )*/
                            .permitAll()
                );
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationSuccessHandler erpSuccessHandler()  {
        return (request, response, authentication) -> {
            authentication.getAuthorities()
                    .forEach(auth->
                        {
                            try{
                                if(auth.getAuthority().equals("ROLE_ADMIN")){
                                    response.sendRedirect("/admin/dashboard");
                                } else if (auth.getAuthority().equals("ROLE_USER")){
                                    response.sendRedirect("/user/dashboard");
                                } else {
                                    response.sendRedirect("/");
                                }
                            } catch (Exception e){
                                throw new RuntimeException(e);
                            }
                        }
                    );
        };
    }


}
