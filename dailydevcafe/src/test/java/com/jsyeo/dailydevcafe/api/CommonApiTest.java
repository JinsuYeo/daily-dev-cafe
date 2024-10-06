//package com.jsyeo.dailydevcafe.api;
//
//import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
//import static org.springframework.restdocs.payload.PayloadDocumentation.*;
//import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;
//
//
//import com.jsyeo.dailydevcafe.domain.member.Member;
//import com.jsyeo.dailydevcafe.dto.request.SignUpRequestDto;
//import com.jsyeo.dailydevcafe.repository.MemberRepository;
//import com.jsyeo.dailydevcafe.security.JwtProvider;
//import io.restassured.RestAssured;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.restdocs.payload.JsonFieldType;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Slf4j
//@RequiredArgsConstructor
//public class CommonApiTest extends ApiTest {
//
//    private final String TEST_EMAIL = "test@test.com";
//
//    @Autowired
//    private MemberRepository memberRepository;
//    @Autowired
//    private JwtProvider jwtProvider;
//    private String bearerToken;
//    private Map<String, Object> requestData = new HashMap<>();
//
//    @BeforeEach
//    void init() {
//        bearerToken = jwtProvider.create(TEST_EMAIL);
//
//        requestData.put("email", TEST_EMAIL);
//        requestData.put("password", "Pa$sw0rd");
//    }
//
//    @Test
//    void forbiddenTest() throws Exception {
//        RestAssured.given(this.documentationSpec
//                        .accept("application/json")
//                        .filter(document("forbidden",
//                                preprocessRequest(modifyUris()
//                                                .scheme("https")
//                                                .host("api.dailydevcafe.com")
//                                                .removePort(),
//                                        modifyHeaders()
//                                                .remove("Vary")
//                                                .remove("Cache-Control")
//                                                .remove("Pragma")
//                                                .remove("Content-Length")
//                                                .remove("Date")
//                                                .remove("Keep-Alive")
//                                                .remove("Connection")
//                                                .remove("Expires")
//                                                .remove("X-Content-Type-Options")
//                                                .remove("X-XSS-Protection")
//                                                .remove("X-Frame-Options")
//                                                .remove("Transfer-Encoding"),
//                                        prettyPrint()),
//                                preprocessResponse(modifyHeaders()
//                                                .remove("Vary")
//                                                .remove("Cache-Control")
//                                                .remove("Pragma")
//                                                .remove("Content-Length")
//                                                .remove("Date")
//                                                .remove("Keep-Alive")
//                                                .remove("Connection")
//                                                .remove("Expires")
//                                                .remove("X-Content-Type-Options")
//                                                .remove("X-XSS-Protection")
//                                                .remove("X-Frame-Options")
//                                                .remove("Transfer-Encoding"),
//                                        prettyPrint()),
//                                responseFields(
//                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("결과 코드"),
//                                        fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지"),
//                                        fieldWithPath("data").type(JsonFieldType.STRING).description("오류 상세 메시지")))))
//                .log().all()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .when()
//                .post("/post")
//                .then()
//                .statusCode(HttpStatus.FORBIDDEN.value())
//                .log().all();
//    }
//
//    @Test
//    void badRequestExceptionTest() {
//
//        RestAssured.given(this.documentationSpec
//                        .accept("application/json")
//                        .filter(document("badRequest",
//                                preprocessRequest(modifyUris()
//                                                .scheme("https")
//                                                .host("api.dailydevcafe.com")
//                                                .removePort(),
//                                        modifyHeaders()
//                                                .remove("Vary")
//                                                .remove("Cache-Control")
//                                                .remove("Pragma")
//                                                .remove("Content-Length")
//                                                .remove("Date")
//                                                .remove("Keep-Alive")
//                                                .remove("Connection")
//                                                .remove("Expires")
//                                                .remove("X-Content-Type-Options")
//                                                .remove("X-XSS-Protection")
//                                                .remove("X-Frame-Options")
//                                                .remove("Transfer-Encoding"),
//                                        prettyPrint()),
//                                preprocessResponse(modifyHeaders()
//                                                .remove("Vary")
//                                                .remove("Cache-Control")
//                                                .remove("Pragma")
//                                                .remove("Content-Length")
//                                                .remove("Date")
//                                                .remove("Keep-Alive")
//                                                .remove("Connection")
//                                                .remove("Expires")
//                                                .remove("X-Content-Type-Options")
//                                                .remove("X-XSS-Protection")
//                                                .remove("X-Frame-Options")
//                                                .remove("Transfer-Encoding"),
//                                        prettyPrint()),
//                                responseFields(
//                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("결과 코드"),
//                                        fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지"),
//                                        fieldWithPath("data").type(JsonFieldType.STRING).description("오류 상세 메시지")))))
//                .log().all()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .when()
//                .post("/auth/signin")
//                .then()
//                .statusCode(HttpStatus.BAD_REQUEST.value())
//                .log().all();
//    }
//
//    @Test
//    void internalSereverExceptionTest() throws Exception {
//    }
//
//
//    private void signUp() {
//        SignUpRequestDto dto = new SignUpRequestDto();
//        dto.setName("memberA");
//        dto.setEmail(TEST_EMAIL);
//        dto.setNickname("nickname");
//        dto.setPassword("Pa$sw0rd");
//        dto.setAgreedPersonal(true);
//
//        Member member = new Member(dto);
//
//        RestAssured.given()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body(member)
//                .when()
//                .port(this.port)
//                .post("/auth/signup")
//                .then().statusCode(HttpStatus.OK.value());
//    }
//
//    private String publishPost() {
//        Map<String, Object> postData = RestAssured.given()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .header("Authorization",
//                        "Bearer " + bearerToken)
//                .body(requestData)
//                .when()
//                .post("/post")
//                .then()
//                .extract().body().jsonPath().get("data");
//        return postData.get("id").toString();
//    }
//}
