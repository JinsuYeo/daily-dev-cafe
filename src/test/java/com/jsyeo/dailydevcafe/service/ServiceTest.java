package com.jsyeo.dailydevcafe.service;

import com.jsyeo.dailydevcafe.AcceptanceTestExecutionListener;
import com.jsyeo.dailydevcafe.dto.request.SignInRequestDto;
import com.jsyeo.dailydevcafe.dto.request.SignUpRequestDto;
import org.springframework.test.context.TestExecutionListeners;

//@TestExecutionListeners(value = {AcceptanceTestExecutionListener.class,}, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
public class ServiceTest {

    SignUpRequestDto createSignUpRequestDto(String email, String nickname) {
        SignUpRequestDto signUpRequestDto = new SignUpRequestDto();
        signUpRequestDto.setName("memberA");
        signUpRequestDto.setNickname(nickname);
        signUpRequestDto.setEmail(email);
        signUpRequestDto.setPassword("Pa$sw0rd");
        signUpRequestDto.setAgreedPersonal(true);
        return signUpRequestDto;
    }

    SignInRequestDto createSignInRequestDto(String email, String password) {
        SignInRequestDto requestDto = new SignInRequestDto();
        requestDto.setEmail(email);
        requestDto.setPassword(password);
        return requestDto;
    }
}
