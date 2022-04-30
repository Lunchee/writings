package lunchee.books.configuration;

import org.jdbi.v3.core.h2.H2DatabasePlugin;
import org.jdbi.v3.spring5.JdbiFactoryBean;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.List;

@Configuration(proxyBeanMethods = false)
public class QueryConfiguration {

    @Bean
    public JdbiFactoryBean jdbi(DataSource dataSource) {
        return new JdbiFactoryBean(dataSource)
                .setPlugins(List.of(new H2DatabasePlugin(), new SqlObjectPlugin()));
    }
}
