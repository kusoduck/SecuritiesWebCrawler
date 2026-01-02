package com.kusoduck.securities.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.kusoduck.securities.repository", // 改成您的 package
        entityManagerFactoryRef = "securitiesEntityManagerFactory",
        transactionManagerRef = "securitiesTransactionManager"
)
public class SecuritiesDbConfig {

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean securitiesEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("securitiesDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.kusoduck.securities.entity") // 改成您的 entity package
                .persistenceUnit("securitiesPU")
                .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager securitiesTransactionManager(
            @Qualifier("securitiesEntityManagerFactory") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
