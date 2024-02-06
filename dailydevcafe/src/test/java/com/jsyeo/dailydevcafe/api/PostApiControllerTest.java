package com.jsyeo.dailydevcafe.api;

import com.jsyeo.dailydevcafe.domain.member.Member;
import com.jsyeo.dailydevcafe.dto.request.SignUpRequestDto;
import com.jsyeo.dailydevcafe.repository.MemberRepository;
import com.jsyeo.dailydevcafe.security.JwtProvider;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@RequiredArgsConstructor
public class PostApiControllerTest extends ApiTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private JwtProvider jwtProvider;
    private String bearerToken;
    private Map<String, Object> requestData = new HashMap<>();

    @BeforeEach
    void init() {
        bearerToken = jwtProvider.create("test@test.com");

        requestData.put("title", "Test Title");
        requestData.put("content", "Publish Post Test");
        requestData.put("category", "Test Category");
    }

    @Test
    void publishPostTest() {

        signUp();

        ExtractableResponse<Response> response = publishPostRequest("/post");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

        Map<String, Object> responseData = response.body().jsonPath().get("data");
        assertThat(responseData.get("title")).isEqualTo("Test Title");
        assertThat(responseData.get("category")).isEqualTo("Test Category");
    }

    @Test
    void getPostTest() {

        signUp();
        Map<String, Object> postData = publishPostRequest("/post").body().jsonPath().get("data");
        String postId = postData.get("id").toString();

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/posts/" + postId)
                .then()
                .log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

        Map<String, Object> responseData = response.body().jsonPath().get("data");
        assertThat(responseData.get("id").toString()).isEqualTo(postId);
        assertThat(responseData.get("title")).isEqualTo("Test Title");
        assertThat(responseData.get("category")).isEqualTo("Test Category");
    }

    private void signUp() {
        SignUpRequestDto dto = new SignUpRequestDto();
        dto.setName("memberA");
        dto.setEmail("test@test.com");
        dto.setNickname("testNickname");
        dto.setPassword("Pa$sw0rd");
        dto.setAgreedPersonal(true);

        Member member = new Member(dto);
        memberRepository.save(member);
    }

    private ExtractableResponse<Response> publishPostRequest(String path) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization",
                        "Bearer " + bearerToken,
                        "Content-Type",
                        ContentType.JSON,
                        "Accept",
                        ContentType.JSON)
                .body(requestData)
                .when()
                .post(path)
                .then()
                .log().all().extract();
    }
}
