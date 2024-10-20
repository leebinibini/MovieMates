package com.nc13.moviemates.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QHistoryEntity is a Querydsl query type for HistoryEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHistoryEntity extends EntityPathBase<HistoryEntity> {

    private static final long serialVersionUID = 1983609522L;

    public static final QHistoryEntity historyEntity = new QHistoryEntity("historyEntity");

    public final DateTimePath<java.util.Date> date = createDateTime("date", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> movieId = createNumber("movieId", Long.class);

    public final StringPath poster = createString("poster");

    public final NumberPath<Long> reservations = createNumber("reservations", Long.class);

    public final StringPath room = createString("room");

    public final StringPath seat = createString("seat");

    public final DateTimePath<java.util.Date> time = createDateTime("time", java.util.Date.class);

    public final StringPath title = createString("title");

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QHistoryEntity(String variable) {
        super(HistoryEntity.class, forVariable(variable));
    }

    public QHistoryEntity(Path<? extends HistoryEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHistoryEntity(PathMetadata metadata) {
        super(HistoryEntity.class, metadata);
    }

}

