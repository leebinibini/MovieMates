package com.nc13.moviemates.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSeatEntity is a Querydsl query type for SeatEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSeatEntity extends EntityPathBase<SeatEntity> {

    private static final long serialVersionUID = 1768443661L;

    public static final QSeatEntity seatEntity = new QSeatEntity("seatEntity");

    public final NumberPath<Integer> column = createNumber("column", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> row = createNumber("row", Integer.class);

    public final NumberPath<Long> scheduleId = createNumber("scheduleId", Long.class);

    public final EnumPath<SeatStatus> status = createEnum("status", SeatStatus.class);

    public QSeatEntity(String variable) {
        super(SeatEntity.class, forVariable(variable));
    }

    public QSeatEntity(Path<? extends SeatEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSeatEntity(PathMetadata metadata) {
        super(SeatEntity.class, metadata);
    }

}

