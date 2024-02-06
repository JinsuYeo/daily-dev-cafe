package com.jsyeo.dailydevcafe.service;

import com.jsyeo.dailydevcafe.AcceptanceTestExecutionListener;
import com.jsyeo.dailydevcafe.domain.Post;
import com.jsyeo.dailydevcafe.domain.member.Member;
import com.jsyeo.dailydevcafe.dto.MemberDto;
import com.jsyeo.dailydevcafe.dto.PostDto;
import com.jsyeo.dailydevcafe.dto.request.PublishPostRequestDto;
import com.jsyeo.dailydevcafe.dto.request.SignUpRequestDto;
import com.jsyeo.dailydevcafe.dto.response.ResponseDto;
import com.jsyeo.dailydevcafe.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
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

        SignUpRequestDto requestDto = createSignUpRequestDto("test@test.com", "test");
        ResponseDto<? extends MemberDto> responseDto = memberService.signUp(requestDto);
        assertThat(responseDto.getCode()).isEqualTo(HttpStatus.OK.value());
    }
    
    @Test
    void publishPostTest() {

        PublishPostRequestDto requestDto = createPublishPostRequestDto();

        ResponseDto<? super PostDto> responseDto = postService.publishPost(TEST_EMAIL, requestDto);

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
        PostDto requestPostDto = (PostDto) postService.publishPost(TEST_EMAIL, requestDto).getData();
        
        //when
        ResponseDto<? super PostDto> responseDto = postService.getPost(requestPostDto.getId());

        //then
        assertThat(responseDto.getCode()).isEqualTo(HttpStatus.OK.value());
        PostDto responsePostDto = (PostDto) responseDto.getData();
        assertThat(responsePostDto.getTitle()).isEqualTo(requestPostDto.getTitle());
        assertThat(responsePostDto.getContent()).isEqualTo(requestPostDto.getContent());
        assertThat(responsePostDto.getCategory()).isEqualTo(requestPostDto.getCategory());
    }

    private PublishPostRequestDto createPublishPostRequestDto() {
        PublishPostRequestDto requestDto = new PublishPostRequestDto();
        requestDto.setTitle(TEST_TITLE);
        requestDto.setContent(TEST_CONTENT);
        requestDto.setCategory(TEST_CATEGORY);
        return requestDto;
    }
}
