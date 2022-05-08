package com.github.lunchee.writings.configuration;

import com.github.lunchee.writings.query.configuration.QueryRepository;
import com.github.lunchee.writings.query.configuration.Query;
import com.github.lunchee.writings.util.AuthorGenerator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.filter.TypeExcludeFilters;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jooq.AutoConfigureJooq;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@BootstrapWith(QueryTestContextBootstrapper.class)
@ExtendWith(SpringExtension.class)
@OverrideAutoConfiguration(enabled = false)
@TypeExcludeFilters(QueryTestTypeExcludeFilter.class)
@AutoConfigureTestDatabase
@AutoConfigureJooq
@ImportAutoConfiguration
@ActiveProfiles("test")
@Import(AuthorGenerator.class)
public @interface QueryTest {

    /**
     * Determines if default filtering should be used with
     * {@link SpringBootApplication @SpringBootApplication}. By default no beans are
     * included.
     *
     * @return if default filters should be used
     * @see #includeFilters()
     * @see #excludeFilters()
     */
    boolean useDefaultFilters() default true;

    /**
     * A set of include filters which can be used to add otherwise filtered beans to the
     * application context.
     *
     * @return include filters to apply
     */
    ComponentScan.Filter[] includeFilters() default {
            @ComponentScan.Filter(QueryRepository.class),
            @ComponentScan.Filter(Query.class),
    };

    /**
     * A set of exclude filters which can be used to filter beans that would otherwise be
     * added to the application context.
     *
     * @return exclude filters to apply
     */
    ComponentScan.Filter[] excludeFilters() default {};
}
