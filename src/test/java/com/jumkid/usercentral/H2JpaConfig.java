package com.jumkid.usercentral;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.jumkid.usercentral.repository")
@EnableTransactionManagement
public class H2JpaConfig {
    // void
}
