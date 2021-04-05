package com.example.generaltask.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfiguration {

    @Bean
    public AuditorAware<String> auditorProvider() {
        /*
            If using spring security, get current user here with
            SecurityContextHolder.getContext().getAuthentication.getName();
         */

        // Dummy user, because no authentication is used on this application
        return () -> Optional.ofNullable("user");
    }
}
