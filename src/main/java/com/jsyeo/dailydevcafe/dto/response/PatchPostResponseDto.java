package com.jsyeo.dailydevcafe.dto.response;

import com.jsyeo.dailydevcafe.dto.PostDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PatchPostResponseDto extends ResponseDto {

    private PatchPostResponseDto(int code, String message, PostDto dto) {
        super(code, message, dto);
    }

    public static PatchPostResponseDto success(PostDto dto) {
        return new PatchPostResponseDto(HttpStatus.OK.value(), "게시글이 수정되었습니다.", dto);
    }

    public static PatchPostResponseDto notExistMember() {
        return new PatchPostResponseDto(HttpStatus.FORBIDDEN.value(), "접근 권한이 없습니다.", null);
    }

    public static PatchPostResponseDto notExistPost() {
        return new PatchPostResponseDto(HttpStatus.BAD_REQUEST.value(), "존재하지 않는 게시글입니다.", null);
    }

    public static PatchPostResponseDto notMatchedMember() {
        return new PatchPostResponseDto(HttpStatus.BAD_REQUEST.value(), "게시글 수정 권한이 없습니다.", null);
    }
}
