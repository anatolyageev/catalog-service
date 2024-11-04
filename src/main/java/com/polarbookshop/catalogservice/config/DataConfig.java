package com.polarbookshop.catalogservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;

@Configuration // Indicates a class as a source of Spring configuration
@EnableJdbcAuditing // Enables auditing for persistent entities
public class DataConfig {
}

//    NOTE In Spring Data JPA, you would use the @EnableJpaAuditing annotation
//    to enable JPA auditing, and you would annotate the entity class with
//    @EntityListeners(AuditingEntityListener.class) to make it listen to
//    audit events, which doesn’t happen automatically as in Spring Data JDBC.