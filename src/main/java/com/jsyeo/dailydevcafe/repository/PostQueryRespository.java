package com.jsyeo.dailydevcafe.repository;

import com.jsyeo.dailydevcafe.domain.Post;
import com.jsyeo.dailydevcafe.domain.QPost;
import com.jsyeo.dailydevcafe.domain.member.QMember;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.jsyeo.dailydevcafe.domain.QPost.*;
import static com.jsyeo.dailydevcafe.domain.member.QMember.member;

@Repository
public class PostQueryRespository {

    private final JPAQueryFactory query;

    public PostQueryRespository(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    public Long countAll(PostSearchCond cond) {
        return query
                .select(post.count())
                .from(post)
                .join(post.member, member)
                .where(
                        nicknameLike(cond.getNickname()),
                        titleLike(cond.getTitle()),
                        contentLike(cond.getContent()),
                        categoryLike(cond.getCategory()
                        )).fetchOne();
    }

    public List<Post> findAll(PostSearchCond cond, Pageable pageable) {

        QPost post = QPost.post;
        QMember member = QMember.member;

        return query
                .select(post)
                .from(post)
                .join(post.member, member)
                .where(
                        nicknameLike(cond.getNickname()),
                        titleLike(cond.getTitle()),
                        contentLike(cond.getContent()),
                        categoryLike(cond.getCategory()
                        ))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(postSort(pageable.getSort()))
                .fetch();
    }

    private BooleanExpression nicknameLike(String nickname) {
        if (StringUtils.hasText(nickname)) {
            return member.nickname.like(nickname);
        }

        return null;
    }

    private BooleanExpression titleLike(String title) {
        if (StringUtils.hasText(title)) {
            return post.title.like("%" + title + "%");
        }

        return null;
    }

    private BooleanExpression contentLike(String content) {
        if (StringUtils.hasText(content)) {
            return post.content.like("%" + content + "%");
        }

        return null;
    }

    private BooleanExpression categoryLike(String category) {
        if (StringUtils.hasText(category)) {
            return post.category.like(category);
        }

        return null;
    }

    private OrderSpecifier<?> postSort(Sort sort) {

        if (!sort.isEmpty()) {

            for (Sort.Order order : sort) {

                switch (order.getProperty()) {
                    case "postDate":
                        return new OrderSpecifier(Order.DESC, post.postDate);
                    case "title":
                        return new OrderSpecifier(Order.ASC, post.title);
                    case "popularity":
                        return new OrderSpecifier(Order.DESC, post.favoriteCount);
                }
            }
        }

        return null;
    }
}
