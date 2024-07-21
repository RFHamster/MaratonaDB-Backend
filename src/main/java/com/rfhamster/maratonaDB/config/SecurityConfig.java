package com.rfhamster.maratonaDB.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.rfhamster.maratonaDB.securityJwt.JwtTokenFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	@Autowired
	private JwtTokenFilter customFilter;
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
    AuthenticationManager authenticationManagerBean(
    		AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

	@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		//@formatter:off
        return http
            .httpBasic(basic -> basic.disable())
            .csrf(csrf -> csrf.disable())
            .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
            		//Teste Controller
            		.requestMatchers(HttpMethod.GET, "/teste").permitAll()
            		//Auth Controller
                    .requestMatchers(HttpMethod.POST, "/auth/signin").permitAll()
                    .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                    .requestMatchers(HttpMethod.PUT, "/auth/refresh/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/auth/register/adm").hasRole("ADMIN")
                    //Dicas Controller
                    .requestMatchers(HttpMethod.DELETE, "/dica/**").hasRole("ADMIN")
                    //Problema Controller
                    .requestMatchers(HttpMethod.GET, "/problema/desativados").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/problema/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PATCH, "/problema/ativar/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PATCH, "/problema/desativar/**").hasRole("ADMIN")
                    //Report Controller
                    .requestMatchers(HttpMethod.GET, "/reports").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/reports/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/reports/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PATCH, "/reports").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PATCH, "/reports/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/reports").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/reports/**").hasRole("ADMIN")
                    //User Controller
                    .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/users/cpf/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/users/rg/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/users/nome/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/users/matricula/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PATCH, "/users/atribuicao/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PATCH, "/users/desabilitar/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PATCH, "/users/habilitar/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")
                    
                    .anyRequest().authenticated()
            )
            .cors(cors -> {})
            .build();
        //@formatter:on
	}
}
