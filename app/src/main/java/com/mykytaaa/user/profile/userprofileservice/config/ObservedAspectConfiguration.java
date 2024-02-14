package com.mykytaaa.user.profile.userprofileservice.config;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for defining a Spring bean of type ObservedAspect.
 * This class is annotated with @Configuration, indicating that it provides
 * bean definitions to the Spring application context.
 */
@Configuration
public class ObservedAspectConfiguration {

    /**
     * Creates and returns a bean of type ObservedAspect.
     *
     * @param observationRegistry The ObservationRegistry instance to be injected into the ObservedAspect.
     * @return An instance of ObservedAspect with the provided ObservationRegistry.
     */
    @Bean
    public ObservedAspect observedAspect(ObservationRegistry observationRegistry) {
        return new ObservedAspect(observationRegistry);
    }
}
