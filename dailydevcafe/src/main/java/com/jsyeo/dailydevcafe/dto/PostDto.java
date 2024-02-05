package com.jsyeo.dailydevcafe.dto;

import com.jsyeo.dailydevcafe.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostDto {

    private Long id;
    private String title;
    private String category;
    private String nickname;
    private LocalDateTime postDate;
    private int favoriteCount;
    private int commentCount;
    private int viewCount;

    public PostDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.category = post.getCategory();
        this.nickname = post.getMember().getNickname();
        this.postDate = post.getPostDate();
        this.favoriteCount = post.getCommentCount();
        this.commentCount = post.getCommentCount();
        this.viewCount = post.getViewCount();
    }
}
