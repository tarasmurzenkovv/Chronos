package com.syngenta.digital.lab.kyiv.chronos.configuration;

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
        if (bean instanceof DataSource) {
            DataSource actualDataSource = (DataSource) bean;
            return ProxyDataSourceBuilder.create(SingleCountQueryExecutionListenerWrapper.TEST_DATA_SOURCE_NAME, actualDataSource)
                    .countQuery(singleQueryCountHolder)
                    .build();
        }
        return bean;
    }
}
