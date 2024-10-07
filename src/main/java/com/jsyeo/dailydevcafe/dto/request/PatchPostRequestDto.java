package com.jsyeo.dailydevcafe.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class PatchPostRequestDto {

    private Long id;

    @NotBlank
    private String title;
    @NotBlank
    private String content;

    private String Category;
}
