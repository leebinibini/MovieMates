package com.nc13.moviemates.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReservationEntity is a Querydsl query type for ReservationEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReservationEntity extends EntityPathBase<ReservationEntity> {

    private static final long serialVersionUID = -1617130902L;

    public static final QReservationEntity reservationEntity = new QReservationEntity("reservationEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> movieId = createNumber("movieId", Long.class);

    public final NumberPath<Long> paymentId = createNumber("paymentId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> reservationDate = createDateTime("reservationDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> scheduleId = createNumber("scheduleId", Long.class);

    public final NumberPath<Long> seatId = createNumber("seatId", Long.class);

    public final StringPath status = createString("status");

    public final NumberPath<Integer> ticketPrice = createNumber("ticketPrice", Integer.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QReservationEntity(String variable) {
        super(ReservationEntity.class, forVariable(variable));
    }

    public QReservationEntity(Path<? extends ReservationEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReservationEntity(PathMetadata metadata) {
        super(ReservationEntity.class, metadata);
    }

}

