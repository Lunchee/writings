package com.github.lunchee.writings.query.configuration;

import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.annotation.MergedAnnotations.SearchStrategy;

public class QueryTestContextBootstrapper extends SpringBootTestContextBootstrapper {

    @Override
    protected String[] getProperties(Class<?> testClass) {
        return MergedAnnotations.from(
                        testClass,
                        SearchStrategy.INHERITED_ANNOTATIONS
                ).get(QueryTest.class)
                .getValue("properties", String[].class)
                .orElse(null);
    }
}
