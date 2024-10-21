package com.nc13.moviemates.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReviewMovieEntity is a Querydsl query type for ReviewMovieEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReviewMovieEntity extends EntityPathBase<ReviewMovieEntity> {

    private static final long serialVersionUID = -193096618L;

    public static final QReviewMovieEntity reviewMovieEntity = new QReviewMovieEntity("reviewMovieEntity");

    public final StringPath content = createString("content");

    public final DateTimePath<java.util.Date> date = createDateTime("date", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lengthPosterUrl = createString("lengthPosterUrl");

    public final NumberPath<Long> movieId = createNumber("movieId", Long.class);

    public final NumberPath<Float> rating = createNumber("rating", Float.class);

    public final NumberPath<Long> reviewId = createNumber("reviewId", Long.class);

    public final StringPath title = createString("title");

    public final NumberPath<Long> writerId = createNumber("writerId", Long.class);

    public QReviewMovieEntity(String variable) {
        super(ReviewMovieEntity.class, forVariable(variable));
    }

    public QReviewMovieEntity(Path<? extends ReviewMovieEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReviewMovieEntity(PathMetadata metadata) {
        super(ReviewMovieEntity.class, metadata);
    }

}

