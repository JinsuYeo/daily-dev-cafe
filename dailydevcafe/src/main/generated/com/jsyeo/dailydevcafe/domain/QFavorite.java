package com.jsyeo.dailydevcafe.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFavorite is a Querydsl query type for Favorite
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFavorite extends EntityPathBase<Favorite> {

    private static final long serialVersionUID = 946008754L;

    public static final QFavorite favorite = new QFavorite("favorite");

    public final StringPath memberId = createString("memberId");

    public final NumberPath<Long> postId = createNumber("postId", Long.class);

    public QFavorite(String variable) {
        super(Favorite.class, forVariable(variable));
    }

    public QFavorite(Path<? extends Favorite> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFavorite(PathMetadata metadata) {
        super(Favorite.class, metadata);
    }

}

