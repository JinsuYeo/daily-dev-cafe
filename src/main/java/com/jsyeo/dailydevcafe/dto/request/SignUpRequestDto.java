package com.jsyeo.dailydevcafe.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class SignUpRequestDto {
    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 2, max = 10)
    private String nickname;

    @NotBlank
    // 8~20자, 적어도 하나의 숫자, 알파벳 소문자, 대문자, 특수문자가 포함되어야하며, 공백이 없어야한다.
    @Pattern(regexp = "^.*(?=.{8,20})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$")
    private String password;

    @NotNull
    @AssertTrue
    private Boolean agreedPersonal;
}
