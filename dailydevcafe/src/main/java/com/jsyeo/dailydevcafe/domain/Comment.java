package com.jsyeo.dailydevcafe.domain;

import com.jsyeo.dailydevcafe.domain.member.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Comment {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String content;

    @NotNull
    private LocalDateTime commentDate;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
