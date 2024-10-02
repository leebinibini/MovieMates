package com.nc13.moviemates.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BucketConfig {
    private final String endpoint = "https://kr.object.ncloudstorage.com";
    private final String regionName = "kr-standard";

    @Value("${naver.storage.accessKey}")
    private String accessKey;

    @Value("${naver.storage.secretKey}")
    private String secretKey;

    @Value("${naver.storage.bucketName}")
    private String bucketName;

    //인풋 스트림, 사진에 대한 크기와 타입을 지정해주는 것이 오브젝트메타데이터
    @Bean
    public AmazonS3 amazonS3Client(){
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);
        return AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, regionName))
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .withPathStyleAccessEnabled(true)
                .build();
    }
}