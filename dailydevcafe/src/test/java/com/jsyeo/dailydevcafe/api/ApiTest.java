package com.jsyeo.dailydevcafe.api;

import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.documentationConfiguration;

import com.jsyeo.dailydevcafe.AcceptanceTestExecutionListener;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@TestExecutionListeners(value = {AcceptanceTestExecutionListener.class,}, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class ApiTest {

        RequestSpecification documentationSpec;

        @LocalServerPort
        int port;

        @BeforeEach
        void setUp(RestDocumentationContextProvider restDocumentation) {
                if (RestAssured.port == RestAssured.UNDEFINED_PORT) {
                        RestAssured.port = port;
                }
                this.documentationSpec = new RequestSpecBuilder()
                        .addFilter(documentationConfiguration(restDocumentation))
                        .build();
        }
}
