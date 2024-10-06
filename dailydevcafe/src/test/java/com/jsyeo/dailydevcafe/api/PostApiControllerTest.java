//package com.jsyeo.dailydevcafe.api;
//
//import com.jsyeo.dailydevcafe.domain.member.Member;
//import com.jsyeo.dailydevcafe.dto.request.SignUpRequestDto;
//import com.jsyeo.dailydevcafe.repository.MemberRepository;
//import com.jsyeo.dailydevcafe.security.JwtProvider;
//import io.restassured.RestAssured;
//import io.restassured.http.ContentType;
//import io.restassured.response.ExtractableResponse;
//import io.restassured.response.Response;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.restdocs.payload.JsonFieldType;
//import org.springframework.restdocs.restassured.RestDocumentationFilter;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static com.querydsl.core.types.Order.DESC;
//import static org.assertj.core.api.Assertions.*;
//import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
//import static org.springframework.restdocs.payload.PayloadDocumentation.*;
//import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
//import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
//import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
//import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;
//
//@Slf4j
//@RequiredArgsConstructor
//public class PostApiControllerTest extends ApiTest {
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
//        requestData.put("title", "Test Title");
//        requestData.put("content", "Publish Post Test");
//        requestData.put("category", "Test Category");
//    }
//
//    @Test
//    void publishPost_successTest() {
//        // 회원가입 O
//        signUp();
//
//        ExtractableResponse<Response> response = publishPostRequest_success("/post");
//
//        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
//
//        Map<String, Object> responseData = response.body().jsonPath().get("data");
//        assertThat(responseData.get("title")).isEqualTo("Test Title");
//        assertThat(responseData.get("category")).isEqualTo("Test Category");
//    }
//
//    @Test
//    void publishPost_failTest() {
//        // 회원가입 X
//
//        ExtractableResponse<Response> response = publishPostRequest_fail("/post");
//
//        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
//        ;
//    }
//
//    @Test
//    void getPost_successTest() {
//
//        signUp();
//        String postId = publishPost();
//
//        ExtractableResponse<Response> response = getPost_success(postId);
//
//        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
//
//        Map<String, Object> responseData = response.body().jsonPath().get("data");
//        assertThat(responseData.get("id").toString()).isEqualTo(postId);
//        assertThat(responseData.get("title")).isEqualTo("Test Title");
//        assertThat(responseData.get("category")).isEqualTo("Test Category");
//    }
//
//    @Test
//    void getPost_failTest() {
//
//        ExtractableResponse<Response> response = getPost_fail("0");
//        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
//    }
//
//    @Test
//    void deletePost_successTest() {
//
//        signUp();
//        String postId = publishPost();
//
//        ExtractableResponse<Response> response = deletePostRequest_success(postId);
//        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
//        assertThat((Integer) response.body().jsonPath().get("data")).isEqualTo(Integer.parseInt(postId));
//    }
//
//    @Test
//    void deletePost_failTest() {
//        signUp();
//
//        ExtractableResponse<Response> response = deletePostRequest_fail("0");
//        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
//        assertThat((Integer) response.body().jsonPath().get("data")).isEqualTo(0);
//    }
//
//    @Test
//    void patchPost_successTest() {
//        signUp();
//        String postId = publishPost();
//
//        ExtractableResponse<Response> response = patchPostRequest_success(postId);
//        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
//
//        Map<String, Object> responseData = response.body().jsonPath().get("data");
//        assertThat(responseData.get("title")).isEqualTo("Test Title");
//        assertThat(responseData.get("category")).isEqualTo("Test Category");
//    }
//
//    @Test
//    void patchPost_notExistMemberTest() {
//
//        signUp();
//        publishPost();
//
//        ExtractableResponse<Response> response = patchPostRequest_notExistMember("0");
//        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
//    }
//
//    @Test
//    void patchPost_notExistPostTest() {
//
//        signUp();
//        String postId = "0";
//
//        ExtractableResponse<Response> response = patchPostRequest_notExistPost(postId);
//        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
//    }
//
//    @Test
//    void patchPost_notMatchedMemberTest() {
//
//        signUp();
//        signUp("test2@test.com", "testNickname2");
//        String token = jwtProvider.create("test2@test.com");
//        String postId = publishPost(token);
//
//        ExtractableResponse<Response> response = patchPostRequest_notMatchedMember(postId);
//        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
//    }
//
//    @Test
//    void getPosts_successTest() {
//        signUp();
//        for (int i = 0; i < 20; i++) {
//            publishPost();
//        }
//
//        ExtractableResponse<Response> response = getPosts_success();
//        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
//    }
//
//    @Test
//    void getPosts_failTest() {
//
//        ExtractableResponse<Response> response = getPosts_fail();
//        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
//    }
//
//    //==== RestAssured ====//
//    private void signUp() {
//        SignUpRequestDto dto = new SignUpRequestDto();
//        dto.setName("memberA");
//        dto.setEmail(TEST_EMAIL);
//        dto.setNickname("testNickname");
//        dto.setPassword("Pa$sw0rd");
//        dto.setAgreedPersonal(true);
//
//        Member member = new Member(dto);
//        memberRepository.save(member);
//    }
//
//    private void signUp(String email, String nickname) {
//        SignUpRequestDto dto = new SignUpRequestDto();
//        dto.setName("memberA");
//        dto.setEmail(email);
//        dto.setNickname(nickname);
//        dto.setPassword("Pa$sw0rd");
//        dto.setAgreedPersonal(true);
//
//        Member member = new Member(dto);
//        memberRepository.save(member);
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
//
//    private String publishPost(String token) {
//        Map<String, Object> postData = RestAssured.given()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .header("Authorization",
//                        "Bearer " + token)
//                .body(requestData)
//                .when()
//                .post("/post")
//                .then()
//                .extract().body().jsonPath().get("data");
//        return postData.get("id").toString();
//    }
//
//    private ExtractableResponse<Response> publishPostRequest_success(String path) {
//        return RestAssured.given(this.documentationSpec
//                        .accept("application/json")
//                        .filter(document("publishPost",
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
//                                requestFields(
//                                        fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
//                                        fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 본문"),
//                                        fieldWithPath("category").type(JsonFieldType.STRING).description("게시글 카테고리")
//                                ),
//                                responseFields(
//                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("결과 코드"),
//                                        fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지"),
//                                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("게시글 아이디"),
//                                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시글 제목"),
//                                        fieldWithPath("data.content").type(JsonFieldType.STRING).description("게시글 본문"),
//                                        fieldWithPath("data.category").type(JsonFieldType.STRING).description("게시글 카테고리"),
//                                        fieldWithPath("data.nickname").type(JsonFieldType.STRING).description("유저 닉네임"),
//                                        fieldWithPath("data.postDate").type(JsonFieldType.STRING).description("게시글 발행 시간"),
//                                        fieldWithPath("data.favoriteCount").type(JsonFieldType.NUMBER).description("게시글 좋아요 개수"),
//                                        fieldWithPath("data.commentCount").type(JsonFieldType.NUMBER).description("게시글 댓글 개수"),
//                                        fieldWithPath("data.viewCount").type(JsonFieldType.NUMBER).description("게시글 방문 개수")
//                                )
//                        ))).log().all()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .header("Authorization",
//                        "Bearer " + bearerToken)
//                .body(requestData)
//                .when()
//                .post(path)
//                .then()
//                .log().all().extract();
//    }
//
//    private ExtractableResponse<Response> publishPostRequest_fail(String path) {
//        return RestAssured.given(this.documentationSpec
//                        .accept("application/json")
//                        .filter(document("publishPostfail",
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
//                                requestFields(
//                                        fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
//                                        fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 본문"),
//                                        fieldWithPath("category").type(JsonFieldType.STRING).description("게시글 카테고리")
//                                ),
//                                responseFields(
//                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("결과 코드"),
//                                        fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지"),
//                                        fieldWithPath("data").type(JsonFieldType.NULL).description("게시글 발행 실패")
//                                )
//                        ))).log().all()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .header("Authorization",
//                        "Bearer " + bearerToken)
//                .body(requestData)
//                .when()
//                .post(path)
//                .then()
//                .log().all().extract();
//    }
//
//    private ExtractableResponse<Response> getPost_success(String postId) {
//        return RestAssured.given(this.documentationSpec
//                        .accept("application/json")
//                        .filter(document("getPost",
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
//                                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("게시글 아이디"),
//                                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시글 제목"),
//                                        fieldWithPath("data.content").type(JsonFieldType.STRING).description("게시글 본문"),
//                                        fieldWithPath("data.category").type(JsonFieldType.STRING).description("게시글 카테고리"),
//                                        fieldWithPath("data.nickname").type(JsonFieldType.STRING).description("유저 닉네임"),
//                                        fieldWithPath("data.postDate").type(JsonFieldType.STRING).description("게시글 발행 시간"),
//                                        fieldWithPath("data.favoriteCount").type(JsonFieldType.NUMBER).description("게시글 좋아요 개수"),
//                                        fieldWithPath("data.commentCount").type(JsonFieldType.NUMBER).description("게시글 댓글 개수"),
//                                        fieldWithPath("data.viewCount").type(JsonFieldType.NUMBER).description("게시글 방문 개수")
//                                ),
//                                pathParameters(
//                                        parameterWithName("postId").description("게시글 아이디")
//                                )
//                        ))).log().all()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .when()
//                .get("/posts/{postId}", postId)
//                .then()
//                .log().all().extract();
//    }
//
//    private ExtractableResponse<Response> getPost_fail(String postId) {
//        return RestAssured.given(this.documentationSpec
//                        .accept("application/json")
//                        .filter(document("getPostFail",
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
//                                        fieldWithPath("data").type(JsonFieldType.NULL).description("게시글 조회 실패")
//
//                                ),
//                                pathParameters(
//                                        parameterWithName("postId").description("게시글 아이디")
//                                )
//                        ))).log().all()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .when()
//                .get("/posts/{postId}", postId)
//                .then()
//                .log().all().extract();
//    }
//
//    private ExtractableResponse<Response> deletePostRequest_success(String postId) {
//        return RestAssured.given(this.documentationSpec
//                        .accept("application/json")
//                        .filter(document("deletePost",
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
//                                        fieldWithPath("data").type(JsonFieldType.NUMBER).description("삭제된 게시글 아이디")
//                                ),
//                                pathParameters(
//                                        parameterWithName("postId").description("게시글 아이디")
//                                )
//                        ))).log().all()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .header("Authorization",
//                        "Bearer " + bearerToken)
//                .when()
//                .delete("/posts/{postId}", postId)
//                .then()
//                .log().all().extract();
//    }
//
//    private ExtractableResponse<Response> deletePostRequest_fail(String postId) {
//        return RestAssured.given(this.documentationSpec
//                        .accept("application/json")
//                        .filter(document("deletePostFail",
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
//                                        fieldWithPath("data").type(JsonFieldType.NUMBER).description("삭제 요청된 게시글 아이디")
//                                )
//                        ))).log().all()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .header("Authorization",
//                        "Bearer " + bearerToken)
//                .when()
//                .delete("/posts/{postId}", postId)
//                .then()
//                .log().all().extract();
//    }
//
//    private ExtractableResponse<Response> patchPostRequest_success(String postId) {
//        return RestAssured.given(this.documentationSpec
//                        .accept("application/json")
//                        .filter(document("patchPost",
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
//                                requestFields(
//                                        fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
//                                        fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 본문"),
//                                        fieldWithPath("category").type(JsonFieldType.STRING).description("게시글 카테고리")
//                                ),
//                                responseFields(
//                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("결과 코드"),
//                                        fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지"),
//                                        fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지"),
//                                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("게시글 아이디"),
//                                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시글 제목"),
//                                        fieldWithPath("data.content").type(JsonFieldType.STRING).description("게시글 본문"),
//                                        fieldWithPath("data.category").type(JsonFieldType.STRING).description("게시글 카테고리"),
//                                        fieldWithPath("data.nickname").type(JsonFieldType.STRING).description("유저 닉네임"),
//                                        fieldWithPath("data.postDate").type(JsonFieldType.STRING).description("게시글 발행 시간"),
//                                        fieldWithPath("data.favoriteCount").type(JsonFieldType.NUMBER).description("게시글 좋아요 개수"),
//                                        fieldWithPath("data.commentCount").type(JsonFieldType.NUMBER).description("게시글 댓글 개수"),
//                                        fieldWithPath("data.viewCount").type(JsonFieldType.NUMBER).description("게시글 방문 개수")
//                                ),
//                                pathParameters(
//                                        parameterWithName("postId").description("게시글 아이디")
//                                )
//                        ))).log().all()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .header("Authorization",
//                        "Bearer " + bearerToken)
//                .body(requestData)
//                .when()
//                .patch("/posts/{postId}", postId)
//                .then()
//                .log().all().extract();
//    }
//
//    private ExtractableResponse<Response> patchPostRequest_notExistMember(String postId) {
//        return RestAssured.given(this.documentationSpec
//                        .accept("application/json")
//                        .filter(document("patchPostNotExistMember",
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
//                                        fieldWithPath("data").type(JsonFieldType.STRING).description("게시글 수정 실패")
//                                )
//                        )))
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body(requestData)
//                .when()
//                .patch("/posts/{postId}", postId)
//                .then()
//                .extract();
//    }
//
//    private ExtractableResponse<Response> patchPostRequest_notExistPost(String postId) {
//        return RestAssured.given(this.documentationSpec
//                        .accept("application/json")
//                        .filter(document("patchPostnotExistPost",
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
//                                        fieldWithPath("data").type(JsonFieldType.NULL).description("게시글 수정 실패")
//                                )
//                        )))
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .header("Authorization",
//                        "Bearer " + bearerToken)
//                .body(requestData)
//                .when()
//                .patch("/posts/{postId}", postId)
//                .then()
//                .extract();
//    }
//
//    private ExtractableResponse<Response> patchPostRequest_notMatchedMember(String postId) {
//        return RestAssured.given(this.documentationSpec
//                        .accept("application/json")
//                        .filter(document("patchPostnotMatchedMember",
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
//                                        fieldWithPath("data").type(JsonFieldType.NULL).description("게시글 수정 실패")
//                                )
//                        )))
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .header("Authorization",
//                        "Bearer " + bearerToken)
//                .body(requestData)
//                .when()
//                .patch("/posts/{postId}", postId)
//                .then()
//                .extract();
//    }
//
//    private ExtractableResponse<Response> getPosts_success() {
//        return RestAssured.given(this.documentationSpec
//                        .accept("application/json")
//                        .filter(document("getPosts",
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
//                                        fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("게시글 아이디"),
//                                        fieldWithPath("data[].title").type(JsonFieldType.STRING).description("게시글 제목"),
//                                        fieldWithPath("data[].content").type(JsonFieldType.STRING).description("게시글 본문"),
//                                        fieldWithPath("data[].category").type(JsonFieldType.STRING).description("게시글 카테고리"),
//                                        fieldWithPath("data[].nickname").type(JsonFieldType.STRING).description("유저 닉네임"),
//                                        fieldWithPath("data[].postDate").type(JsonFieldType.STRING).description("게시글 발행 시간"),
//                                        fieldWithPath("data[].favoriteCount").type(JsonFieldType.NUMBER).description("게시글 좋아요 개수"),
//                                        fieldWithPath("data[].commentCount").type(JsonFieldType.NUMBER).description("게시글 댓글 개수"),
//                                        fieldWithPath("data[].viewCount").type(JsonFieldType.NUMBER).description("게시글 방문 개수"),
//                                        fieldWithPath("count").type(JsonFieldType.NUMBER).description("게시글 총 개수")
//                                ),
//                                pathParameters(
//                                        parameterWithName("title").optional().description("게시글 제목"),
//                                        parameterWithName("content").optional().description("게시글 본문"),
//                                        parameterWithName("category").optional().description("게시글 카테고리"),
//                                        parameterWithName("nickname").optional().description("유저 닉네임"),
//                                        parameterWithName("pageNumber").optional().description("페이지 번호"),
//                                        parameterWithName("pageSize").optional().description("페이지 당 게시글 개수: default=20, max size=50"),
//                                        parameterWithName("sort").optional().description("정렬 기준: postDate=최신순, title=제목순, popularity=좋아요순")
//                                )
//                        ))).log().all()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .when()
//                .get("/posts?title={title}&content={content}&category={category}&nickname={nickname}&page={pageNumber}&size={pageSize}&sort={sort}",
//                        requestData.get("title"),
//                        requestData.get("content"),
//                        requestData.get("category"),
//                        "testNickname",
//                        0,
//                        20,
//                        "postDate"
//                )
//                .then()
//                .log().all().extract();
//    }
//
//    private ExtractableResponse<Response> getPosts_fail() {
//        return RestAssured.given(this.documentationSpec
//                        .accept("application/json")
//                        .filter(document("getPostsFail",
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
//                                        fieldWithPath("data").type(JsonFieldType.NULL).description("게시글 조회 실패"),
//                                        fieldWithPath("count").type(JsonFieldType.NUMBER).description("게시글 총 개수")
//                                )
//                        ))).log().all()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .when()
//                .get("/posts")
//                .then()
//                .log().all().extract();
//    }
//}
