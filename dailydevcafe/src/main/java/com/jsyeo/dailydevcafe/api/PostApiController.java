package com.jsyeo.dailydevcafe.api;

import com.jsyeo.dailydevcafe.dto.PostDto;
import com.jsyeo.dailydevcafe.dto.request.PatchPostRequestDto;
import com.jsyeo.dailydevcafe.dto.request.PublishPostRequestDto;
import com.jsyeo.dailydevcafe.dto.response.*;
import com.jsyeo.dailydevcafe.repository.PostSearchCond;
import com.jsyeo.dailydevcafe.service.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    @GetMapping("/posts")
    public ResponseEntity<? super GetPostsResponseDto> getPosts(@ModelAttribute("postSearchCond") PostSearchCond postSearchCond,
                                                                @PageableDefault(size = 20, sort = "postDate") Pageable pageable) {
        ResponseDto<? super List<PostDto>> responseDto = postService.findPosts(postSearchCond, pageable);
        return ResponseEntity.status(responseDto.getCode()).body(responseDto);
    }

    @PostMapping("/post")
    public ResponseEntity<? super PublishPostResponseDto> publishPost(@AuthenticationPrincipal String email,
                                                                      @RequestBody @Valid PublishPostRequestDto publishPostRequestDto) {

        ResponseDto responseDto = postService.publish(email, publishPostRequestDto);
        return ResponseEntity.status(responseDto.getCode()).body(responseDto);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<? super GetPostResponseDto> getPost(@PathVariable("id") @NotNull Long postId) {

        ResponseDto responseDto = postService.get(postId);
        return ResponseEntity.status(responseDto.getCode()).body(responseDto);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<? super DeletePostResponseDto> deletePost(@PathVariable("id") @NotNull Long postId) {

        ResponseDto responseDto = postService.delete(postId);
        return ResponseEntity.status(responseDto.getCode()).body(responseDto);
    }

    @PatchMapping("/posts/{id}")
    public ResponseEntity<? super PatchPostResponseDto> patchPost(@PathVariable("id") String id,
                                                                  @AuthenticationPrincipal String email,
                                                                  @RequestBody @Valid PatchPostRequestDto patchPostRequestDto) {
        patchPostRequestDto.setId(Long.parseLong(id));
        ResponseDto responseDto = postService.patch(email, patchPostRequestDto);
        return ResponseEntity.status(responseDto.getCode()).body(responseDto);
    }
}
