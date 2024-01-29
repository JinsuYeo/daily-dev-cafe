package com.jsyeo.dailydevcafe.api;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestExecutionListeners;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@TestExecutionListeners(value = {AcceptanceTestExecutionListener.class,}, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
public class ApiTest {

        @LocalServerPort
        private int port;

        @BeforeEach
        void setUp() {
                if (RestAssured.port == RestAssured.UNDEFINED_PORT) {
                        RestAssured.port = port;
                }
        }
}
