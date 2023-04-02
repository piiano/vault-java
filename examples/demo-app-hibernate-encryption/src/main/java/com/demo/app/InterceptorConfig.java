package com.demo.app;

import com.piiano.vault.orm.encryption.TransformationInterceptor;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

// In order to support fields annotated @Transformation you must inject the TransformationInterceptor.
// This configuration class is an example of how to do this.
@Configuration
@ComponentScan(" com.piiano.vault.orm.encryption")
public class InterceptorConfig {
    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(TransformationInterceptor interceptor) {
        return props -> props.put("hibernate.session_factory.interceptor", interceptor);
    }
}
