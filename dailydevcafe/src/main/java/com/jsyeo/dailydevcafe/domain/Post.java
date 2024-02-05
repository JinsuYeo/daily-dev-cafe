package com.jsyeo.dailydevcafe.domain;

import com.jsyeo.dailydevcafe.domain.member.Member;
import com.jsyeo.dailydevcafe.dto.request.PublishPostRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String title;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String content;

    private String category;
    private LocalDateTime postDate;

    private int favoriteCount = 0;
    private int commentCount = 0;
    private int viewCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Post(String title, String content, String category, LocalDateTime postDate, int favoriteCount, int commentCount, int viewCount, Member member) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.postDate = postDate;
        this.favoriteCount = favoriteCount;
        this.commentCount = commentCount;
        this.viewCount = viewCount;
        this.member = member;
    }

    public Post(PublishPostRequestDto dto, Member member) {
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.category = dto.getCategory();
        this.postDate = LocalDateTime.now();
        this.member = member;
    }
}
