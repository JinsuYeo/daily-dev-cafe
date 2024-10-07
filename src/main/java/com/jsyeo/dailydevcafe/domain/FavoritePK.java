package com.jsyeo.dailydevcafe.domain;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class FavoritePK implements Serializable {
    private String memberId;
    private Long postId;
}
