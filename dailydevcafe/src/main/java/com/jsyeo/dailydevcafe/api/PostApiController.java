package com.jsyeo.dailydevcafe.api;

import com.jsyeo.dailydevcafe.dto.request.PublishPostRequestDto;
import com.jsyeo.dailydevcafe.dto.response.GetPostResponseDto;
import com.jsyeo.dailydevcafe.dto.response.PublishPostResponseDto;
import com.jsyeo.dailydevcafe.dto.response.ResponseDto;
import com.jsyeo.dailydevcafe.service.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    @GetMapping("/posts")
    public String posts() {
        return "ok";
    }

    @PostMapping("/post")
    public ResponseEntity<? super PublishPostResponseDto> publishPost(@AuthenticationPrincipal String email,
                                                                      @RequestBody @Valid PublishPostRequestDto publishPostRequestDto) {

        ResponseDto responseDto = postService.publishPost(email, publishPostRequestDto);
        return ResponseEntity.status(responseDto.getCode()).body(responseDto);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<? super GetPostResponseDto> getPost(@PathVariable("id") @NotNull Long postId) {

        ResponseDto responseDto = postService.getPost(postId);
        return ResponseEntity.status(responseDto.getCode()).body(responseDto);
    }
}
