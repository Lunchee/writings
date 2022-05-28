package com.github.lunchee.writings.query.configuration;

import org.springframework.boot.test.autoconfigure.filter.StandardAnnotationCustomizableTypeExcludeFilter;

public class QueryTestTypeExcludeFilter extends StandardAnnotationCustomizableTypeExcludeFilter<QueryTest> {

    protected QueryTestTypeExcludeFilter(Class<?> testClass) {
        super(testClass);
    }
}
