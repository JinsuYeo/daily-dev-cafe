package com.jsyeo.dailydevcafe.repository;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class PostSearchCond {

    private String nickname;
    private String title;
    private String content;
    private String category;
}
