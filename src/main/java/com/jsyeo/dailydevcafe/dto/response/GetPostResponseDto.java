package com.jsyeo.dailydevcafe.dto.response;

import com.jsyeo.dailydevcafe.dto.PostDto;
import org.springframework.http.HttpStatus;

public class GetPostResponseDto extends ResponseDto {

    private GetPostResponseDto(int code, String message, PostDto dto) {
        super(code, message, dto);
    }

    public static GetPostResponseDto success(PostDto dto) {
        return new GetPostResponseDto(HttpStatus.OK.value(), "게시물 불러오기 성공.", dto);
    }

    public static GetPostResponseDto notExistPost() {
        return new GetPostResponseDto(HttpStatus.BAD_REQUEST.value(), "존재하지 않는 게시물입니다.", null);
    }
}
