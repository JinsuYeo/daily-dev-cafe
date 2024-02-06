package com.jsyeo.dailydevcafe.service;

import com.jsyeo.dailydevcafe.domain.Post;
import com.jsyeo.dailydevcafe.domain.member.Member;
import com.jsyeo.dailydevcafe.dto.PostDto;
import com.jsyeo.dailydevcafe.dto.request.PublishPostRequestDto;
import com.jsyeo.dailydevcafe.dto.response.GetPostResponseDto;
import com.jsyeo.dailydevcafe.dto.response.DeletePostResponseDto;
import com.jsyeo.dailydevcafe.dto.response.PublishPostResponseDto;
import com.jsyeo.dailydevcafe.dto.response.ResponseDto;
import com.jsyeo.dailydevcafe.repository.MemberRepository;
import com.jsyeo.dailydevcafe.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public ResponseDto<? super PostDto> publish(String email,
                                                    PublishPostRequestDto requestDto) {

        if (!StringUtils.hasText(email) || !memberRepository.existsByEmail(email
        )) {
            return PublishPostResponseDto.notExistUser();
        }

        Member findMember = memberRepository.findByEmail(email);
        Post post = new Post(requestDto, findMember);
        postRepository.save(post);

        return PublishPostResponseDto.success(new PostDto(post));
    }

    @Transactional(readOnly = true)
    public ResponseDto<? super PostDto> get(Long postId) {

        if (!postRepository.existsById(postId)) {
            return GetPostResponseDto.notExistPost();
        }

        Post findPost = postRepository.findById(postId).orElseThrow();
        return GetPostResponseDto.success(new PostDto(findPost));
    }

    public ResponseDto<? super Long> delete(Long postId) {

        if (!postRepository.existsById(postId)) {
            return DeletePostResponseDto.notExistPost(postId);
        }

        postRepository.deleteById(postId);
        return DeletePostResponseDto.success(postId);
    }
}
