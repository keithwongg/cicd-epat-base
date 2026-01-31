package sg.edu.nus.iss.d13revision.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.security.web.header.writers.CrossOriginEmbedderPolicyHeaderWriter;
import org.springframework.security.web.header.writers.CrossOriginOpenerPolicyHeaderWriter;
import org.springframework.security.web.header.writers.CrossOriginResourcePolicyHeaderWriter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/actuator/health", "/actuator/prometheus").permitAll()
                .anyRequest().permitAll()
            )
            .headers(headers -> headers
                // Enable HSTS (Strict-Transport-Security)
                .httpStrictTransportSecurity(hsts -> hsts
                    .includeSubDomains(true)
                    .maxAgeInSeconds(31536000)
                )
                // Prevent MIME type sniffing
                .contentTypeOptions(contentTypeOptions -> {})
                // Mitigate clickjacking attacks
                .frameOptions(frameOptions -> frameOptions.deny())
                // Enable XSS protection
                .xssProtection(xss -> {})
                // Add referrer policy
                .referrerPolicy(referrer -> referrer
                    .policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
                )
                // Add Cross-Origin policies to mitigate Spectre vulnerability
                .crossOriginEmbedderPolicy(coep -> coep
                    .policy(CrossOriginEmbedderPolicyHeaderWriter.CrossOriginEmbedderPolicy.REQUIRE_CORP)
                )
                .crossOriginOpenerPolicy(coop -> coop
                    .policy(CrossOriginOpenerPolicyHeaderWriter.CrossOriginOpenerPolicy.SAME_ORIGIN)
                )
                .crossOriginResourcePolicy(corp -> corp
                    .policy(CrossOriginResourcePolicyHeaderWriter.CrossOriginResourcePolicy.SAME_ORIGIN)
                )
                // Add cache control headers
                .cacheControl(cache -> {})
            )
            .csrf(csrf -> csrf.disable()); // Disable CSRF for simplicity; enable in production if needed

        return http.build();
    }
}
