package com.jsyeo.dailydevcafe.service;

import com.jsyeo.dailydevcafe.dto.MemberDto;
import com.jsyeo.dailydevcafe.dto.PostDto;
import com.jsyeo.dailydevcafe.dto.request.PatchPostRequestDto;
import com.jsyeo.dailydevcafe.dto.request.PublishPostRequestDto;
import com.jsyeo.dailydevcafe.dto.request.SignUpRequestDto;
import com.jsyeo.dailydevcafe.dto.response.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Transactional
@SpringBootTest
public class PostServiceTest extends ServiceTest {

    @Autowired
    private PostService postService;
    @Autowired
    private MemberService memberService;

    private final String TEST_EMAIL = "test@test.com";
    private final String TEST_TITLE = "Test Title";
    private final String TEST_CONTENT = "Publish Post Test";
    private final String TEST_CATEGORY = "Test Category";

    @BeforeEach
    void init() {

        SignUpRequestDto requestDto = createSignUpRequestDto(TEST_EMAIL, "test");
        ResponseDto<? extends MemberDto> responseDto = memberService.signUp(requestDto);
        assertThat(responseDto.getCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void publishPostTest() {

        PublishPostRequestDto requestDto = createPublishPostRequestDto();

        ResponseDto<? super PostDto> responseDto = postService.publish(TEST_EMAIL, requestDto);

        assertThat(responseDto.getCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(responseDto.getMessage()).isEqualTo("게시글이 발행되었습니다.");

        PostDto data = (PostDto) responseDto.getData();
        assertThat(data.getTitle()).isEqualTo(requestDto.getTitle());
        assertThat(data.getCategory()).isEqualTo(requestDto.getCategory());
    }

    @Test
    void getPostTest() {

        // given
        PublishPostRequestDto requestDto = createPublishPostRequestDto();
        PostDto postDto = (PostDto) postService.publish(TEST_EMAIL, requestDto).getData();

        //when
        ResponseDto<? super PostDto> responseDto = postService.get(postDto.getId());

        //then
        assertThat(responseDto.getCode()).isEqualTo(HttpStatus.OK.value());
        PostDto responsePostDto = (PostDto) responseDto.getData();
        assertThat(responsePostDto.getTitle()).isEqualTo(postDto.getTitle());
        assertThat(responsePostDto.getContent()).isEqualTo(postDto.getContent());
        assertThat(responsePostDto.getCategory()).isEqualTo(postDto.getCategory());
    }

    @Test
    void deletePostTest() {

        // given
        PublishPostRequestDto requestDto = createPublishPostRequestDto();
        PostDto postDto = (PostDto) postService.publish(TEST_EMAIL, requestDto).getData();

        //when
        ResponseDto<? super Long> responseDto = postService.delete(postDto.getId());

        //then
        assertThat(responseDto.getCode()).isEqualTo(HttpStatus.OK.value());
        assertThat((Long) responseDto.getData()).isEqualTo(postDto.getId());
    }

    @Test
    void patchPostTest() {

        // given
        PostDto postDto = (PostDto) postService.publish(TEST_EMAIL, createPublishPostRequestDto()).getData();

        //when
        PatchPostRequestDto requestDto = new PatchPostRequestDto();
        requestDto.setId(postDto.getId());
        requestDto.setTitle("Updated Title");
        requestDto.setContent("Updated Content");
        requestDto.setCategory("Updated Category");

        ResponseDto<? super PostDto> responseDto = postService.patch(TEST_EMAIL, requestDto);

        // then
        assertThat(responseDto.getCode()).isEqualTo(HttpStatus.OK.value());
        PostDto data = (PostDto) responseDto.getData();
        assertThat(data.getTitle()).isEqualTo(requestDto.getTitle());
        assertThat(data.getCategory()).isEqualTo(requestDto.getCategory());
    }

    private PublishPostRequestDto createPublishPostRequestDto() {
        PublishPostRequestDto requestDto = new PublishPostRequestDto();
        requestDto.setTitle(TEST_TITLE);
        requestDto.setContent(TEST_CONTENT);
        requestDto.setCategory(TEST_CATEGORY);
        return requestDto;
    }
}
