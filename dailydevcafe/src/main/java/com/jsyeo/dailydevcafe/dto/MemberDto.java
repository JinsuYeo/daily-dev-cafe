package com.jsyeo.dailydevcafe.dto;

import com.jsyeo.dailydevcafe.domain.Member;
import com.jsyeo.dailydevcafe.domain.MemberType;
import com.jsyeo.dailydevcafe.dto.request.SignInRequestDto;
import com.jsyeo.dailydevcafe.dto.request.SignUpRequestDto;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberDto {

    private String id;
    private String name;
    private String nickname;
    private String email;
    private String password;

    public static MemberDto createMemberDto(SignUpRequestDto signUpRequestDto) {
        MemberDto memberDto = new MemberDto();
        memberDto.setName(signUpRequestDto.getName());
        memberDto.setEmail(signUpRequestDto.getEmail());
        memberDto.setNickname(signUpRequestDto.getNickname());
        memberDto.setPassword(signUpRequestDto.getPassword());
        return memberDto;
    }

    public static MemberDto createMemberDto(Member member) {
        MemberDto memberDto = new MemberDto();
        memberDto.setId(member.getId());
        memberDto.setName(member.getName());
        memberDto.setEmail(member.getEmail());
        memberDto.setNickname(member.getNickname());
        memberDto.setPassword(member.getPassword());
        return memberDto;
    }
}
