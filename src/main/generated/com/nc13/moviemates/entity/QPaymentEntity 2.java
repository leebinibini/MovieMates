package com.nc13.moviemates.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPaymentEntity is a Querydsl query type for PaymentEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPaymentEntity extends EntityPathBase<PaymentEntity> {

    private static final long serialVersionUID = 158362276L;

    public static final QPaymentEntity paymentEntity = new QPaymentEntity("paymentEntity");

    public final NumberPath<Integer> amount = createNumber("amount", Integer.class);

    public final StringPath buyerName = createString("buyerName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath impUid = createString("impUid");

    public final StringPath location = createString("location");

    public final StringPath merchantUid = createString("merchantUid");

    public final StringPath movie = createString("movie");

    public final DateTimePath<java.util.Date> paymentDate = createDateTime("paymentDate", java.util.Date.class);

    public final StringPath paymentMethod = createString("paymentMethod");

    public final StringPath paymentStatus = createString("paymentStatus");

    public final NumberPath<Long> seatId = createNumber("seatId", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QPaymentEntity(String variable) {
        super(PaymentEntity.class, forVariable(variable));
    }

    public QPaymentEntity(Path<? extends PaymentEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPaymentEntity(PathMetadata metadata) {
        super(PaymentEntity.class, metadata);
    }

}

