package com.syngenta.digital.lab.kyiv.chronos;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;

import com.syngenta.digital.lab.kyiv.chronos.configuration.security.service.JwtTokenProvider;
import com.syngenta.digital.lab.kyiv.chronos.service.ClockService;
import com.syngenta.digital.lab.kyiv.chronos.utils.db.utils.GeneratedQueryStatisticsQueryExecutionListener;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.Function;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ChronosApplicationEntryPoint.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class,
        MockitoTestExecutionListener.class, GeneratedQueryStatisticsQueryExecutionListener.class})
public class BaseIntegrationTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @MockBean
    private ClockService clockService;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;


    protected ObjectMapper objectMapper = new ObjectMapper();

    @LocalServerPort
    private int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
        Mockito.when(jwtTokenProvider.generateToken(Mockito.any())).thenReturn("token");
        Mockito.when(jwtTokenProvider.getJwtFromRequest(Mockito.any())).thenReturn("token");
        Mockito.when(jwtTokenProvider.validateToken(Mockito.any())).thenReturn(true);
        Mockito.when(jwtTokenProvider.getUserIdFromJWT(Mockito.any())).thenReturn(1L);

        Mockito.when(authenticationManager.authenticate(Mockito.any())).thenReturn(new TestingAuthenticationToken("login", "password"));
        Mockito.when(clockService.nowTime()).thenReturn(LocalDateTime.of(2017, 1, 1, 1, 1));
        Mockito.when(clockService.now()).thenReturn(LocalDate.of(2017, 1, 1));
    }

    @Test
    public void noOp() {
        log.info("No OP");
    }

    public static void validateBadResponse(Response response) {
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
        ResponseBody body = response.getBody();
        Assertions.assertThat(body).isNotNull();
        String asGeneralResponseString = body.asString();
        Assertions.assertThat(asGeneralResponseString).isNotEmpty();
    }

    protected RequestSpecification getRestAssured() {
        return RestAssured.given().header("Authorization", "Bearer fake_token");
    }
}
