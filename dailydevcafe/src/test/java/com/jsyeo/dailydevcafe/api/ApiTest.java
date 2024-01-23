package com.jsyeo.dailydevcafe.api;

import io.restassured.RestAssured;
import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
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
