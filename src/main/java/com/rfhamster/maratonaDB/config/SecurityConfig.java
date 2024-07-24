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
                    .requestMatchers(HttpMethod.POST, "/api/auth/v1/signin").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/auth/v1/register").permitAll()
                    .requestMatchers(HttpMethod.PUT, "/api/auth/v1/refresh/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/auth/v1/register/adm").hasRole("ADMIN")
                    //Dicas Controller
                    .requestMatchers(HttpMethod.DELETE, "/api/dica/v1/**").hasRole("ADMIN")
                    //Problema Controller
                    .requestMatchers(HttpMethod.GET, "/api/problema/v1/desativados").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/problema/v1/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PATCH, "/api/problema/v1/ativar/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PATCH, "/api/problema/v1/desativar/**").hasRole("ADMIN")
                    //Report Controller
                    .requestMatchers(HttpMethod.GET, "/api/reports/v1").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/reports/v1/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/reports/v1**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PATCH, "/api/reports/v1").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PATCH, "/api/reports/v1/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/reports/v1").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/reports/v1/**").hasRole("ADMIN")
                    //User Controller
                    .requestMatchers(HttpMethod.GET, "/api/users/v1").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/users/v1/cpf/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/users/v1/rg/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/users/v1/nome/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/users/v1/matricula/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PATCH, "/api/users/v1/atribuicao/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PATCH, "/api/users/v1/desabilitar/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PATCH, "/api/users/v1/habilitar/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/users/v1/**").hasRole("ADMIN")
                    //Open API
                    .requestMatchers(HttpMethod.GET, "/v3/api-docs").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api-docs/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
                    .anyRequest().authenticated()
            )
            .cors(cors -> {})
            .build();
        //@formatter:on
	}
}
