package com.jsyeo.dailydevcafe.repository;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@EqualsAndHashCode
@NoArgsConstructor
public class PostSearchCond {

    private String nickname;
    private String title;
    private String content;
    private String category;
}
