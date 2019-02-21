package com.syngenta.digital.lab.kyiv.chronos.utils.db.utils;

import lombok.NoArgsConstructor;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

@Service
@NoArgsConstructor
public class GeneratedQueryStatisticsQueryExecutionListener extends AbstractTestExecutionListener {
    @Autowired
    private SingleCountQueryExecutionListenerWrapper singleCountQueryExecutionListenerWrapper;

    @Override
    public void beforeTestClass(TestContext testContext) {
        testContext.getApplicationContext()
                .getAutowireCapableBeanFactory()
                .autowireBean(this);
    }

    @Override
    public void beforeTestMethod(TestContext testContext) {
        singleCountQueryExecutionListenerWrapper.reset();
    }


    @Override
    public void afterTestMethod(TestContext testContext) {
        ExpectedGeneratedQueryNumber annotation = testContext.getTestMethod().getAnnotation(ExpectedGeneratedQueryNumber.class);
        ExpectedGeneratedQueryNumbers annotations = testContext.getTestMethod().getAnnotation(ExpectedGeneratedQueryNumbers.class);
        if (annotation != null) {
            GeneratedQueryStatisticsQueryExecutionListener.verifyNumberOfTheGeneratedQueriesForType(singleCountQueryExecutionListenerWrapper, annotation);
        }
        if (annotations != null) {
            GeneratedQueryStatisticsQueryExecutionListener.verifyNumberOfTheGeneratedQueriesForType(singleCountQueryExecutionListenerWrapper, annotations.value());
        }
    }

    private static void verifyNumberOfTheGeneratedQueriesForType(SingleCountQueryExecutionListenerWrapper singleCountQueryExecutionListenerWrapper,
                                                                 ExpectedGeneratedQueryNumber[] annotations) {
        for (ExpectedGeneratedQueryNumber annotation : annotations) {
            verifyNumberOfTheGeneratedQueriesForType(singleCountQueryExecutionListenerWrapper, annotation);
        }
    }

    private static void verifyNumberOfTheGeneratedQueriesForType(SingleCountQueryExecutionListenerWrapper singleCountQueryExecutionListenerWrapper,
                                                                 ExpectedGeneratedQueryNumber annotation) {
        long expectedNumberOfQueries = annotation.expectedNumber();
        QueryType queryType = annotation.queryType();
        long queryCount = GeneratedQueryStatisticsQueryExecutionListener.getQueryCount(singleCountQueryExecutionListenerWrapper, queryType);
        Assertions.assertThat(queryCount)
                .withFailMessage("Expected number of '%s' queries is '%d', but actual number is '%s'", queryType, expectedNumberOfQueries, queryCount)
                .isEqualTo(expectedNumberOfQueries);
    }

    private static long getQueryCount(SingleCountQueryExecutionListenerWrapper singleCountQueryExecutionListenerWrapper, QueryType queryType) {
        switch (queryType) {
            case SELECT:
                return singleCountQueryExecutionListenerWrapper.select();
            case INSERT:
                return singleCountQueryExecutionListenerWrapper.insert();
            case DELETE:
                return singleCountQueryExecutionListenerWrapper.delete();
            case UPDATE:
                return singleCountQueryExecutionListenerWrapper.update();
            default:
                throw new RuntimeException();
        }
    }
}
