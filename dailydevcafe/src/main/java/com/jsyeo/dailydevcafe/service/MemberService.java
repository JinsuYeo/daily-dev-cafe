package com.jsyeo.dailydevcafe.service;

import com.jsyeo.dailydevcafe.domain.member.Member;
import com.jsyeo.dailydevcafe.dto.request.SignInRequestDto;
import com.jsyeo.dailydevcafe.dto.request.SignUpRequestDto;
import com.jsyeo.dailydevcafe.dto.response.ResponseDto;
import com.jsyeo.dailydevcafe.dto.MemberDto;
import com.jsyeo.dailydevcafe.dto.response.SignInResponseDto;
import com.jsyeo.dailydevcafe.dto.response.SignUpResponseDto;
import com.jsyeo.dailydevcafe.repository.MemberRepository;
import com.jsyeo.dailydevcafe.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public ResponseDto<? extends MemberDto> signUp(SignUpRequestDto requestDto) {

        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        Member member = new Member(requestDto);
        log.info("member.getEmail()={}, memberRepository.existsByEmail(member.getEmail())={}", member.getEmail(), memberRepository.existsByEmail(member.getEmail()));

        if(memberRepository.existsByEmail(member.getEmail())) {
            return SignUpResponseDto.duplicateEmail(new MemberDto(member));
        }
        
        if (memberRepository.existsByNickname(member.getNickname())) {
            return SignUpResponseDto.duplicateNickname(new MemberDto(member));
        }

        memberRepository.save(member);
        MemberDto responseMemberDto = new MemberDto(member);

        return SignUpResponseDto.success(responseMemberDto);
    }

    public ResponseDto<? extends MemberDto> signIn(SignInRequestDto requestDto) {

        String email = requestDto.getEmail();
        Member findMember = memberRepository.findByEmail(email);

        if (findMember == null
                || !passwordEncoder.matches(requestDto.getPassword(), findMember.getPassword())) {
            return SignInResponseDto.fail(new MemberDto(findMember));
        }

        return SignInResponseDto.success(new MemberDto(findMember), jwtProvider.create(email));
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
}
