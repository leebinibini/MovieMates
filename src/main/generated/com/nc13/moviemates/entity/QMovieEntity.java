package com.nc13.moviemates.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMovieEntity is a Querydsl query type for MovieEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMovieEntity extends EntityPathBase<MovieEntity> {

    private static final long serialVersionUID = -627770610L;

    public static final QMovieEntity movieEntity = new QMovieEntity("movieEntity");

    public final StringPath actors = createString("actors");

    public final StringPath ageClass = createString("ageClass");

    public final NumberPath<Integer> booking = createNumber("booking", Integer.class);

    public final StringPath director = createString("director");

    public final StringPath genre = createString("genre");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lengthPosterUrl = createString("lengthPosterUrl");

    public final StringPath plot = createString("plot");

    public final StringPath posterUrl = createString("posterUrl");

    public final NumberPath<Double> rate = createNumber("rate", Double.class);

    public final StringPath releaseDate = createString("releaseDate");

    public final NumberPath<Integer> runningTime = createNumber("runningTime", Integer.class);

    public final StringPath title = createString("title");

    public final StringPath widthPosterUrl = createString("widthPosterUrl");

    public QMovieEntity(String variable) {
        super(MovieEntity.class, forVariable(variable));
    }

    public QMovieEntity(Path<? extends MovieEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMovieEntity(PathMetadata metadata) {
        super(MovieEntity.class, metadata);
    }

}

