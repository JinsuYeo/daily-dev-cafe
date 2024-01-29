package com.jsyeo.dailydevcafe.domain;

import com.jsyeo.dailydevcafe.domain.member.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Post {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @NotBlank
    @Column(length = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime postDate;

    private int favoriteCount = 0;
    private int commentCount = 0;
    private int viewCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
