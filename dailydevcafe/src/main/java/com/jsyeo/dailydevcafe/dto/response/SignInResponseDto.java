package com.jsyeo.dailydevcafe.dto.response;

import com.jsyeo.dailydevcafe.dto.MemberDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter @Setter
public class SignInResponseDto extends ResponseDto {

    private String token;
    private int expirationTime;

    private SignInResponseDto(int code, String message, MemberDto dto, String token) {
        super(code, message, dto);
        this.token = token;
        this.expirationTime = 3600;
    }

    public static SignInResponseDto success(MemberDto dto, String token) {
        return new SignInResponseDto(HttpStatus.OK.value(), "로그인 성공", dto, token);
    }

    public static ResponseDto fail() {
        return new ResponseDto(HttpStatus.BAD_REQUEST.value(), "로그인 정보가 일치하지 않습니다.", null);
    }
}
