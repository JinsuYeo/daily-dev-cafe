package com.jsyeo.dailydevcafe.dto.response;

import com.jsyeo.dailydevcafe.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class ResponseMemberDto {

    private String id;
    private String name;
    private String email;
    private String nickname;

    public ResponseMemberDto(Member member) {
        if (member != null) {
            this.id = member.getId();
            this.name = member.getName();
            this.email = member.getEmail();
            this.nickname = member.getNickname();
        }
    }
}
