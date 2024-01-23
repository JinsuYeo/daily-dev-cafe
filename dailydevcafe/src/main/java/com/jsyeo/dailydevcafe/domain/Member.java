package com.jsyeo.dailydevcafe.domain;

import com.jsyeo.dailydevcafe.dto.request.SignUpRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "member_id")
    private String id;

    @NotEmpty
    private String name;

    @NotEmpty
    @Column(unique = true)
    private String nickname;

    @NotEmpty
    @Column(unique = true)
    private String email;

    @NotEmpty
    private String password;

    @Enumerated(EnumType.STRING)
    private MemberType type = MemberType.APP;

    public Member(SignUpRequestDto dto) {
        this.name = dto.getName();
        this.nickname = dto.getNickname();
        this.email = dto.getEmail();
        this.password = dto.getPassword();
    }

    public Member(String name, String nickname, String email, MemberType type) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = "Pa$sw0rd";
        this.type = type;
    }
}
