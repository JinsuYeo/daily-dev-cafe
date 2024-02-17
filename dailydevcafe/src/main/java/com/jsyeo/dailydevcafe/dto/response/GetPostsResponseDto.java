package com.jsyeo.dailydevcafe.dto.response;

import com.jsyeo.dailydevcafe.dto.PostDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class GetPostsResponseDto extends ResponseDto {

    private Long count;

    private GetPostsResponseDto(int code, String message, List<PostDto> dto, Long count) {
        super(code, message, dto);
        this.count = count;
    }

    public static GetPostsResponseDto success(List<PostDto> dto, Long count) {
        return new GetPostsResponseDto(HttpStatus.OK.value(), "게시물 불러오기 성공.", dto, count);
    }

    public static GetPostsResponseDto notExistPost() {
        return new GetPostsResponseDto(HttpStatus.BAD_REQUEST.value(), "존재하지 않는 게시물입니다.", null, 0L);
    }
}
