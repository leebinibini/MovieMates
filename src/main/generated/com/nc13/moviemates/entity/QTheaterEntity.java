package com.nc13.moviemates.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTheaterEntity is a Querydsl query type for TheaterEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTheaterEntity extends EntityPathBase<TheaterEntity> {

    private static final long serialVersionUID = -2091631505L;

    public static final QTheaterEntity theaterEntity = new QTheaterEntity("theaterEntity");

    public final NumberPath<Integer> capacity = createNumber("capacity", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath location = createString("location");

    public final NumberPath<Long> movieId = createNumber("movieId", Long.class);

    public final StringPath name = createString("name");

    public final StringPath room = createString("room");

    public QTheaterEntity(String variable) {
        super(TheaterEntity.class, forVariable(variable));
    }

    public QTheaterEntity(Path<? extends TheaterEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTheaterEntity(PathMetadata metadata) {
        super(TheaterEntity.class, metadata);
    }

}

