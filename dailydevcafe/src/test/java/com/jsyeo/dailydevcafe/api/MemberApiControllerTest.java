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

    private Map<String, Object> requestData;
    private Map<String, Object> requestDataDuplicatedEmail;
    private Map<String, Object> requestDataDuplicatedNickname;

    @BeforeEach
    void init() {
        requestData = createRequestData("test@test.com", "nickname");
        requestDataDuplicatedEmail = createRequestData("test@test.com", "nickname2");
        requestDataDuplicatedNickname = createRequestData("test2@test.com", "nickname");
    }

    @Test
    void signUp_successTest() {
        ExtractableResponse<Response> response = signUpRequest_success();
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void signUp_duplicateEmailTest() {

        signUp();

        ExtractableResponse<Response> response = signUpRequest_duplicateEmail();
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void signUp_duplicateNicknameTest() {

        signUp();

        ExtractableResponse<Response> response = signUpRequest_duplicateNickname();
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void signIn_sucessTest() {
        signUp();

        ExtractableResponse<Response> response = signInRequest_success();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void signIn_failTest() {

        ExtractableResponse<Response> response = signInRequest_fail();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    private Map<String, Object> createRequestData(String email, String nickname) {
        Map<String, Object> requestDataDuplicatedEmail = new HashMap<>();
        requestDataDuplicatedEmail.put("name", "memberA");
        requestDataDuplicatedEmail.put("email", email);
        requestDataDuplicatedEmail.put("nickname", nickname);
        requestDataDuplicatedEmail.put("password", "Pa$sw0rd");
        requestDataDuplicatedEmail.put("agreedPersonal", true);
        return requestDataDuplicatedEmail;
    }

    private void signUp() {
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestData)
                .when()
                .port(this.port)
                .post("/auth/signup")
                .then().statusCode(HttpStatus.OK.value());
    }

    private ExtractableResponse<Response> signUpRequest_success() {
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

    private ExtractableResponse<Response> signUpRequest_duplicateEmail() {
        return RestAssured.given(this.documentationSpec
                        .accept("application/json")
                        .filter(document("signUpDuplicateEmail",
                                preprocessRequest(modifyUris()
                                                .scheme("http")
                                                .host("localhost")
                                                .removePort(),
                                        prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("결과 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지"),
                                        fieldWithPath("data").type(JsonFieldType.NULL).description("회원가입 실패"))))
                )
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestDataDuplicatedEmail)
                .when()
                .port(this.port)
                .post("/auth/signup")
                .then()
                .extract();
    }

    private ExtractableResponse<Response> signUpRequest_duplicateNickname() {
        return RestAssured.given(this.documentationSpec
                        .accept("application/json")
                        .filter(document("signUpDuplicateNickname",
                                preprocessRequest(modifyUris()
                                                .scheme("http")
                                                .host("localhost")
                                                .removePort(),
                                        prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("결과 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지"),
                                        fieldWithPath("data").type(JsonFieldType.NULL).description("회원가입 실패"))))
                )
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestDataDuplicatedNickname)
                .when()
                .port(this.port)
                .post("/auth/signup")
                .then()
                .extract();
    }

    private ExtractableResponse<Response> signInRequest_success() {
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

    private ExtractableResponse<Response> signInRequest_fail() {
        return RestAssured.given(this.documentationSpec
                        .accept("application/json")
                        .filter(document("signInFail",
                                preprocessRequest(modifyUris()
                                                .scheme("http")
                                                .host("localhost")
                                                .removePort(),
                                        prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("결과 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지"),
                                        fieldWithPath("data").type(JsonFieldType.NULL).description("로그인 실패")
                                )
                        )))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\n" +
                        "    \"email\": \"test@test.com\",\n" +
                        "    \"password\": \"Pa$sw0rd\"\n" +
                        "}")
                .when()
                .port(this.port)
                .post("/auth/signin")
                .then()
                .extract();
    }
}