package com.jsyeo.dailydevcafe.api;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;


import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@Slf4j
class MemberApiControllerTest extends ApiTest {

    private Map<String, Object> requestData = new HashMap<>();

    @BeforeEach
    void init() {
        requestData.put("name", "memberA");
        requestData.put("email", "test@test.com");
        requestData.put("nickname", "nickname");
        requestData.put("password", "Pa$sw0rd");
        requestData.put("agreedPersonal", true);
    }

    @Test
    void signUpTest() {
        ExtractableResponse<Response> response = memberSignUpRequest();
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void signInTest() {
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestData)
                .when()
                .port(this.port)
                .post("/auth/signup")
                .then().statusCode(HttpStatus.OK.value());

        ExtractableResponse<Response> response = memberSignInRequest();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private ExtractableResponse<Response> memberSignUpRequest() {
        return RestAssured.given(this.documentationSpec
                .accept("application/json")
                .filter(document("signUp",
                                preprocessRequest(modifyUris()
                                        .scheme("http")
                                        .host("localhost")
                                        .removePort(),
                                        prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("유저 이름"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("유저 이메일"),
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("유저 닉네임"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("유저 비밀번호"),
                                fieldWithPath("agreedPersonal").type(JsonFieldType.BOOLEAN).description("개인정보 수집 동의")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("결과 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("유저 아이디"),
                                fieldWithPath("data.email").type(JsonFieldType.STRING).description("유저 이메일"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("유저 이름"),
                                fieldWithPath("data.nickname").type(JsonFieldType.STRING).description("유저 닉네임"))))
                ).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestData)
                .when()
                .port(this.port)
                .post("/auth/signup")
                .then()
                .log().all().extract();
    }

    private ExtractableResponse<Response> memberSignInRequest() {
        return RestAssured.given(this.documentationSpec
                        .accept("application/json")
                        .filter(document("signIn",
                                preprocessRequest(modifyUris()
                                        .scheme("http")
                                        .host("localhost")
                                        .removePort(),
                                        prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("유저 이메일"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("유저 비밀번호")
                                ),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("결과 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지"),
                                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("유저 아이디"),
                                        fieldWithPath("data.email").type(JsonFieldType.STRING).description("유저 이메일"),
                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("유저 이름"),
                                        fieldWithPath("data.nickname").type(JsonFieldType.STRING).description("유저 닉네임"),
                                        fieldWithPath("token").type(JsonFieldType.STRING).description("유저 토큰"),
                                        fieldWithPath("expirationTime").type(JsonFieldType.NUMBER).description("토큰 유지 시간")
                                )
                        ))).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\n" +
                        "    \"email\": \"test@test.com\",\n" +
                        "    \"password\": \"Pa$sw0rd\"\n" +
                        "}")
                .when()
                .port(this.port)
                .post("/auth/signin")
                .then()
                .log().all().extract();
    }
}