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
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;

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
        Map<String, Object> postData = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization",
                        "Bearer " + bearerToken,
                        "Content-Type",
                        ContentType.JSON,
                        "Accept",
                        ContentType.JSON)
                .body(requestData)
                .when()
                .post("/post")
                .then()
                .extract().body().jsonPath().get("data");
        String postId = postData.get("id").toString();

        ExtractableResponse<Response> response = RestAssured.given(this.documentationSpec
                        .accept("application/json")
                        .filter(document("getPost",
                                preprocessRequest(modifyUris()
                                        .scheme("http")
                                        .host("localhost")
                                        .removePort(),
                                        prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("결과 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지"),
                                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("게시글 아이디"),
                                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시글 제목"),
                                        fieldWithPath("data.content").type(JsonFieldType.STRING).description("게시글 본문"),
                                        fieldWithPath("data.category").type(JsonFieldType.STRING).description("게시글 카테고리"),
                                        fieldWithPath("data.nickname").type(JsonFieldType.STRING).description("유저 닉네임"),
                                        fieldWithPath("data.postDate").type(JsonFieldType.STRING).description("게시글 발행 시간"),
                                        fieldWithPath("data.favoriteCount").type(JsonFieldType.NUMBER).description("게시글 좋아요 개수"),
                                        fieldWithPath("data.commentCount").type(JsonFieldType.NUMBER).description("게시글 댓글 개수"),
                                        fieldWithPath("data.viewCount").type(JsonFieldType.NUMBER).description("게시글 방문 개수")
                                ),
                                pathParameters(
                                        parameterWithName("postId").description("게시글 아이디")
                                )
                        ))).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/posts/{postId}", postId)
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
        return RestAssured.given(this.documentationSpec
                        .accept("application/json")
                        .filter(document("publishPost",
                                preprocessRequest(modifyUris()
                                        .scheme("http")
                                        .host("localhost")
                                        .removePort(),
                                        prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 본문"),
                                        fieldWithPath("category").type(JsonFieldType.STRING).description("게시글 카테고리")
                                ),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("결과 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지"),
                                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("게시글 아이디"),
                                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시글 제목"),
                                        fieldWithPath("data.content").type(JsonFieldType.STRING).description("게시글 본문"),
                                        fieldWithPath("data.category").type(JsonFieldType.STRING).description("게시글 카테고리"),
                                        fieldWithPath("data.nickname").type(JsonFieldType.STRING).description("유저 닉네임"),
                                        fieldWithPath("data.postDate").type(JsonFieldType.STRING).description("게시글 발행 시간"),
                                        fieldWithPath("data.favoriteCount").type(JsonFieldType.NUMBER).description("게시글 좋아요 개수"),
                                        fieldWithPath("data.commentCount").type(JsonFieldType.NUMBER).description("게시글 댓글 개수"),
                                        fieldWithPath("data.viewCount").type(JsonFieldType.NUMBER).description("게시글 방문 개수")
                                )
                        ))).log().all()
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
