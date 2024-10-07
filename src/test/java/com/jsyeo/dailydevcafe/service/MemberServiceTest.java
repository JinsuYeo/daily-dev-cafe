package com.jsyeo.dailydevcafe.service;

import com.jsyeo.dailydevcafe.AcceptanceTestExecutionListener;
import com.jsyeo.dailydevcafe.dto.request.SignInRequestDto;
import com.jsyeo.dailydevcafe.dto.request.SignUpRequestDto;
import com.jsyeo.dailydevcafe.dto.response.ResponseDto;
import com.jsyeo.dailydevcafe.dto.MemberDto;
import com.jsyeo.dailydevcafe.dto.response.SignInResponseDto;
import com.jsyeo.dailydevcafe.dto.response.SignUpResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberServiceTest extends ServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    void 회원가입() {
        // 정상 처리
        SignUpRequestDto requestDto = createSignUpRequestDto("test@test.com", "test");

        ResponseDto responseDto = memberService.signUp(requestDto);
        assertThat(responseDto.getCode()).isEqualTo(HttpStatus.OK.value());

        // 중복 오류
        requestDto.setEmail("test2@test.com");
        ResponseDto responseDtoNicknameDuplicated = memberService.signUp(requestDto);
        assertThat(responseDtoNicknameDuplicated.getMessage()).isEqualTo(SignUpResponseDto.duplicateNickname().getMessage());

        requestDto.setEmail("test@test.com");
        requestDto.setNickname("test2");
        ResponseDto responseDtoEmailDuplicated = memberService.signUp(requestDto);
        assertThat(responseDtoEmailDuplicated.getMessage()).isEqualTo(SignUpResponseDto.duplicateEmail().getMessage());
    }

    @Test
    void 로그인() {
        // 정상 처리
        SignUpRequestDto signUpRequestDto = createSignUpRequestDto("test@test.com", "test");
        memberService.signUp(signUpRequestDto);

        SignInRequestDto requestDto = createSignInRequestDto("test@test.com", "Pa$sw0rd");

        ResponseDto responseDto = memberService.signIn(requestDto);

        assertThat(responseDto.getCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(responseDto.getMessage()).isEqualTo(SignInResponseDto.success(new MemberDto(), "").getMessage());

        // 로그인 정보가 일치하지 않는 경우
        SignInRequestDto invalidEmailRequestDto = createSignInRequestDto("test2@test.com", "Pa$sw0rd");
        ResponseDto invalidEmailResponseDto = memberService.signIn(invalidEmailRequestDto);

        assertThat(invalidEmailResponseDto.getCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(invalidEmailResponseDto.getMessage()).isEqualTo(SignInResponseDto.fail().getMessage());

        SignInRequestDto invalidPasswordRequestDto = createSignInRequestDto("test@test.com", "Pas$w0rd");
        ResponseDto invalidPasswordResponseDto = memberService.signIn(invalidPasswordRequestDto);

        assertThat(invalidPasswordResponseDto.getCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(invalidPasswordResponseDto.getMessage()).isEqualTo(SignInResponseDto.fail().getMessage());
    }

}