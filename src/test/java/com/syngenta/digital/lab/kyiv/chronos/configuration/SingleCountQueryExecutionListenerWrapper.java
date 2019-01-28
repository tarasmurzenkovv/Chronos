package com.syngenta.digital.lab.kyiv.chronos.configuration;

import net.ttddyy.dsproxy.listener.SingleQueryCountHolder;
import org.springframework.stereotype.Service;

@Service
public class SingleCountQueryExecutionListenerWrapper extends SingleQueryCountHolder {

    public static final String TEST_DATA_SOURCE_NAME = "dataSource";

    public long select() {
        return super.getOrCreateQueryCount(TEST_DATA_SOURCE_NAME).getSelect();
    }

    public long insert() {
        return super.getOrCreateQueryCount(TEST_DATA_SOURCE_NAME).getInsert();
    }

    public long delete() {
        return super.getOrCreateQueryCount(TEST_DATA_SOURCE_NAME).getDelete();
    }

    public long update() {
        return super.getOrCreateQueryCount(TEST_DATA_SOURCE_NAME).getUpdate();
    }

    public void reset() {
        super.clear();
    }
}
