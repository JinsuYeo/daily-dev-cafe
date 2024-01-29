package com.jsyeo.dailydevcafe.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Getter;

@Entity
@Getter
@IdClass(FavoritePK.class)
public class Favorite {

    @Id
    public String memberId;

    @Id
    public Long postId;
}
