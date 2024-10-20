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

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> payment_amount = createNumber("payment_amount", Integer.class);

    public final DateTimePath<java.util.Date> payment_date = createDateTime("payment_date", java.util.Date.class);

    public final StringPath payment_method = createString("payment_method");

    public final StringPath payment_status = createString("payment_status");

    public final NumberPath<Long> reservation_id = createNumber("reservation_id", Long.class);

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

