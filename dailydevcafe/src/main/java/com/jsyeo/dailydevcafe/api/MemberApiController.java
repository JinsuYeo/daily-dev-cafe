package com.jsyeo.dailydevcafe.api;

import com.jsyeo.dailydevcafe.dto.request.SignInRequestDto;
import com.jsyeo.dailydevcafe.dto.request.SignUpRequestDto;
import com.jsyeo.dailydevcafe.dto.response.ResponseDto;
import com.jsyeo.dailydevcafe.dto.response.SignInResponseDto;
import com.jsyeo.dailydevcafe.dto.response.SignUpResponseDto;
import com.jsyeo.dailydevcafe.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/auth/signup")
    public ResponseEntity<? super SignUpResponseDto> signUp(@RequestBody @Valid SignUpRequestDto signUpRequestDto) {

        ResponseDto responseDto = memberService.signUp(signUpRequestDto);

        return ResponseEntity.status(responseDto.getCode()).body(responseDto);
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<? super SignInResponseDto> signIn(@RequestBody @Valid SignInRequestDto signInRequestDto) {

        ResponseDto responseDto = memberService.signIn(signInRequestDto);

        return ResponseEntity.status(responseDto.getCode()).body(responseDto);
    }

    @GetMapping("/auth/oauth2")
    public String oauth2Login() {
        return "OAuth Login";
    }


}
