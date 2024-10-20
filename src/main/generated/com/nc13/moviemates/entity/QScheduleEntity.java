package com.nc13.moviemates.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QScheduleEntity is a Querydsl query type for ScheduleEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QScheduleEntity extends EntityPathBase<ScheduleEntity> {

    private static final long serialVersionUID = -1369967713L;

    public static final QScheduleEntity scheduleEntity = new QScheduleEntity("scheduleEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> movieId = createNumber("movieId", Long.class);

    public final DatePath<java.time.LocalDate> showDate = createDate("showDate", java.time.LocalDate.class);

    public final TimePath<java.time.LocalTime> showTime = createTime("showTime", java.time.LocalTime.class);

    public final NumberPath<Long> theaterId = createNumber("theaterId", Long.class);

    public QScheduleEntity(String variable) {
        super(ScheduleEntity.class, forVariable(variable));
    }

    public QScheduleEntity(Path<? extends ScheduleEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QScheduleEntity(PathMetadata metadata) {
        super(ScheduleEntity.class, metadata);
    }

}

