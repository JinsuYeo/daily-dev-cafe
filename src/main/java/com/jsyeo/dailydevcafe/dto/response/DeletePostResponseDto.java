package com.jsyeo.dailydevcafe.dto.response;

import com.jsyeo.dailydevcafe.dto.PostDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class DeletePostResponseDto extends ResponseDto<Long> {
    public DeletePostResponseDto(int code, String message, Long postId) {
        super(code, message, postId);
    }

    public static ResponseDto<? super Long> success(Long postId) {
        return new DeletePostResponseDto(HttpStatus.OK.value(), "게시물 삭제 성공.", postId);
    }

    public static ResponseDto<? super Long> notExistPost(Long postId) {
        return new DeletePostResponseDto(HttpStatus.BAD_REQUEST.value(), "존재하지 않는 게시물입니다.", postId);
    }
}
