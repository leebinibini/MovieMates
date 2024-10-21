package com.nc13.moviemates.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPosterEntity is a Querydsl query type for PosterEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPosterEntity extends EntityPathBase<PosterEntity> {

    private static final long serialVersionUID = 392547093L;

    public static final QPosterEntity posterEntity = new QPosterEntity("posterEntity");

    public final StringPath filename = createString("filename");

    public final StringPath filepath = createString("filepath");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> movieId = createNumber("movieId", Long.class);

    public final StringPath url = createString("url");

    public QPosterEntity(String variable) {
        super(PosterEntity.class, forVariable(variable));
    }

    public QPosterEntity(Path<? extends PosterEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPosterEntity(PathMetadata metadata) {
        super(PosterEntity.class, metadata);
    }

}

