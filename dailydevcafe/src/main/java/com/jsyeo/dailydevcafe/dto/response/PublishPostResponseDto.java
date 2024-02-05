package com.jsyeo.dailydevcafe.dto.response;

import com.jsyeo.dailydevcafe.dto.MemberDto;
import com.jsyeo.dailydevcafe.dto.PostDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
public class PublishPostResponseDto extends ResponseDto {

    private PublishPostResponseDto(int code, String message, PostDto dto) {
        super(code, message, dto);
    }

    public static PublishPostResponseDto success(PostDto dto) {
        return new PublishPostResponseDto(HttpStatus.OK.value(), "게시글이 발행되었습니다.", dto);
    }

    public static PublishPostResponseDto notExistUser() {
        return new PublishPostResponseDto(HttpStatus.BAD_REQUEST.value(), "존재하지 않는 회원입니다.", null);
    }
}
