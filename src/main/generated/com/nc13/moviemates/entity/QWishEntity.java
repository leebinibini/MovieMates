package com.nc13.moviemates.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWishEntity is a Querydsl query type for WishEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWishEntity extends EntityPathBase<WishEntity> {

    private static final long serialVersionUID = 1588211759L;

    public static final QWishEntity wishEntity = new QWishEntity("wishEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> movieId = createNumber("movieId", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QWishEntity(String variable) {
        super(WishEntity.class, forVariable(variable));
    }

    public QWishEntity(Path<? extends WishEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWishEntity(PathMetadata metadata) {
        super(WishEntity.class, metadata);
    }

}

