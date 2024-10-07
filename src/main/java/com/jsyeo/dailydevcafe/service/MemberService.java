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
import java.util.Optional;

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
        Optional<Member> findMember = memberRepository.findByEmail(member.getEmail());

        if(findMember.isPresent()) {
            return SignUpResponseDto.duplicateEmail();
        }

        findMember = memberRepository.findByNickname(member.getNickname());
        if (findMember.isPresent()) {
            return SignUpResponseDto.duplicateNickname();
        }

        memberRepository.save(member);
        MemberDto responseMemberDto = new MemberDto(member);

        return SignUpResponseDto.success(responseMemberDto);
    }

    public ResponseDto<? extends MemberDto> signIn(SignInRequestDto requestDto) {

        String email = requestDto.getEmail();
        Optional<Member> findMember = memberRepository.findByEmail(email);

        if (findMember.isEmpty()
                || !passwordEncoder.matches(requestDto.getPassword(), findMember.get().getPassword())) {
            return SignInResponseDto.fail();
        }

        return SignInResponseDto.success(new MemberDto(findMember.get()), jwtProvider.create(email));
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
}
