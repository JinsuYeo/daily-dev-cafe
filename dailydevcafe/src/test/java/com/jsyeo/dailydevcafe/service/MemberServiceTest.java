package com.jsyeo.dailydevcafe.service;

import com.jsyeo.dailydevcafe.dto.request.SignInRequestDto;
import com.jsyeo.dailydevcafe.dto.request.SignUpRequestDto;
import com.jsyeo.dailydevcafe.dto.response.ResponseDto;
import com.jsyeo.dailydevcafe.dto.response.ResponseMemberDto;
import com.jsyeo.dailydevcafe.dto.response.SignInResponseDto;
import com.jsyeo.dailydevcafe.dto.response.SignUpResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class MemberServiceTest {

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
        assertThat(responseDtoNicknameDuplicated.getMessage()).isEqualTo(SignUpResponseDto.duplicateNickname(new ResponseMemberDto()).getMessage());

        requestDto.setEmail("test@test.com");
        requestDto.setNickname("test2");
        ResponseDto responseDtoEmailDuplicated = memberService.signUp(requestDto);
        assertThat(responseDtoEmailDuplicated.getMessage()).isEqualTo(SignUpResponseDto.duplicateEmail(new ResponseMemberDto()).getMessage());
    }

    @Test
    void 로그인() {
        // 정상 처리
        SignUpRequestDto signUpRequestDto = createSignUpRequestDto("test@test.com", "test");
        memberService.signUp(signUpRequestDto);

        SignInRequestDto requestDto = createSignInRequestDto("test@test.com", "Pa$sw0rd");

        ResponseDto responseDto = memberService.signIn(requestDto);

        assertThat(responseDto.getCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(responseDto.getMessage()).isEqualTo(SignInResponseDto.success(new ResponseMemberDto(), "").getMessage());

        // 로그인 정보가 일치하지 않는 경우
        SignInRequestDto invalidEmailRequestDto = createSignInRequestDto("test2@test.com", "Pa$sw0rd");
        ResponseDto invalidEmailResponseDto = memberService.signIn(invalidEmailRequestDto);

        assertThat(invalidEmailResponseDto.getCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(invalidEmailResponseDto.getMessage()).isEqualTo(SignInResponseDto.fail(new ResponseMemberDto()).getMessage());

        SignInRequestDto invalidPasswordRequestDto = createSignInRequestDto("test@test.com", "Pas$w0rd");
        ResponseDto invalidPasswordResponseDto = memberService.signIn(invalidPasswordRequestDto);

        assertThat(invalidPasswordResponseDto.getCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(invalidPasswordResponseDto.getMessage()).isEqualTo(SignInResponseDto.fail(new ResponseMemberDto()).getMessage());
    }

    private SignUpRequestDto createSignUpRequestDto(String email, String nickname) {
        SignUpRequestDto signUpRequestDto = new SignUpRequestDto();
        signUpRequestDto.setName("memberA");
        signUpRequestDto.setNickname(nickname);
        signUpRequestDto.setEmail(email);
        signUpRequestDto.setPassword("Pa$sw0rd");
        signUpRequestDto.setAgreedPersonal(true);
        return signUpRequestDto;
    }

    private SignInRequestDto createSignInRequestDto(String email, String password) {
        SignInRequestDto requestDto = new SignInRequestDto();
        requestDto.setEmail(email);
        requestDto.setPassword(password);
        return requestDto;
    }
}