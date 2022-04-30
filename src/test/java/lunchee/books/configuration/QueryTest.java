package lunchee.books.configuration;

import lunchee.books.utility.Query;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@DataJdbcTest(includeFilters = @ComponentScan.Filter(Query.class))
@ImportAutoConfiguration(QueryConfiguration.class)
@ActiveProfiles("test")
public @interface QueryTest {
}
