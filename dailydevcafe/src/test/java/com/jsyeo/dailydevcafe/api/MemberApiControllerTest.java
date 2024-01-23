package com.jsyeo.dailydevcafe.api;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MemberApiControllerTest extends ApiTest{

    @Test
    @Order(1)
    void signUpTest() {
        ExtractableResponse<Response> response = memberSignUpRequest();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @Order(2)
    void signInTest() {
        ExtractableResponse<Response> response = memberSignInRequest();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private ExtractableResponse<Response> memberSignUpRequest() {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\n" +
                        "    \"name\": \"memberA\",\n" +
                        "    \"email\": \"test@test.com\",\n" +
                        "    \"nickname\": \"test\",\n" +
                        "    \"password\": \"Pa$sw0rd\",\n" +
                        "    \"agreedPersonal\": true\n" +
                        "}")
                .when()
                .post("/auth/signup")
                .then()
                .log().all().extract();
    }

    private ExtractableResponse<Response> memberSignInRequest() {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\n" +
                        "    \"email\": \"test@test.com\",\n" +
                        "    \"password\": \"Pa$sw0rd\"\n" +
                        "}")
                .when()
                .post("/auth/signin")
                .then()
                .log().all().extract();
    }


}