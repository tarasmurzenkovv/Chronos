package com.syngenta.digital.lab.kyiv.chronos.utils.db.utils;

import lombok.RequiredArgsConstructor;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@RequiredArgsConstructor
public class DataSourceBeanPostProcessor implements BeanPostProcessor {
    private final SingleCountQueryExecutionListenerWrapper singleQueryCountHolder;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        return (bean instanceof DataSource) ? wrapAroundQueryCounter((DataSource) bean) : bean;
    }

    private Object wrapAroundQueryCounter(DataSource actualDataSource) {
        return ProxyDataSourceBuilder.create(SingleCountQueryExecutionListenerWrapper.TEST_DATA_SOURCE_NAME, actualDataSource)
                .countQuery(singleQueryCountHolder)
                .build();
    }
}
