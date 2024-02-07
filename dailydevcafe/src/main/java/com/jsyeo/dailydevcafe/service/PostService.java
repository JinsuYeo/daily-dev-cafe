package com.jsyeo.dailydevcafe.service;

import com.jsyeo.dailydevcafe.domain.Post;
import com.jsyeo.dailydevcafe.domain.member.Member;
import com.jsyeo.dailydevcafe.dto.PostDto;
import com.jsyeo.dailydevcafe.dto.request.PatchPostRequestDto;
import com.jsyeo.dailydevcafe.dto.request.PublishPostRequestDto;
import com.jsyeo.dailydevcafe.dto.response.*;
import com.jsyeo.dailydevcafe.repository.MemberRepository;
import com.jsyeo.dailydevcafe.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Transactional
    public ResponseDto<? super PostDto> publish(String email,
                                                    PublishPostRequestDto requestDto) {

        if (!StringUtils.hasText(email) || !memberRepository.existsByEmail(email
        )) {
            return PublishPostResponseDto.notExistUser();
        }

        Member findMember = memberRepository.findByEmail(email).orElse(new Member());
        Post post = new Post(requestDto, findMember);
        postRepository.save(post);

        return PublishPostResponseDto.success(new PostDto(post));
    }

    public ResponseDto<? super PostDto> get(Long postId) {

        if (!postRepository.existsById(postId)) {
            return GetPostResponseDto.notExistPost();
        }

        Post findPost = postRepository.findById(postId).orElseThrow();
        return GetPostResponseDto.success(new PostDto(findPost));
    }

    @Transactional
    public ResponseDto<? super Long> delete(Long postId) {

        if (!postRepository.existsById(postId)) {
            return DeletePostResponseDto.notExistPost(postId);
        }

        postRepository.deleteById(postId);
        return DeletePostResponseDto.success(postId);
    }

    @Transactional
    public ResponseDto<? super PostDto> patch(String userEmail, PatchPostRequestDto requestDto) {

        Optional<Post> findPost = postRepository.findById(requestDto.getId());

        if (findPost.isEmpty()) {
            return PatchPostResponseDto.notExistPost();
        }

        if (!StringUtils.hasText(userEmail) || !memberRepository.existsByEmail(userEmail)) {
            return PatchPostResponseDto.notExistMember();
        }

        Post post = findPost.get();
        if (!post.getMember().getEmail().equals(userEmail)) {
            return PatchPostResponseDto.notMatchedMember();
        }

        post.update(requestDto);

        return PatchPostResponseDto.success(new PostDto(post));
    }

}
