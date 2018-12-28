package com.syngenta.digital.lab.kyiv.chronos;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import com.fasterxml.jackson.databind.ObjectMapper;


@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class, MockitoTestExecutionListener.class})
public class BaseIntegrationTest {
    @Value("${server.port}")
    protected int definedPort;

    protected ObjectMapper objectMapper = new ObjectMapper();

    RequestSpecification getPreConfiguredRestAssured() {
        return RestAssured
                .given()
                .port(this.definedPort)
                .contentType(ContentType.JSON);
    }
}
