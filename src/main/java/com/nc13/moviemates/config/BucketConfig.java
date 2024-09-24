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

    @Value("${naver.storage.bucket}")
    private String bucketName;

    @Value("${naver.storage.region}")
    private String region;

    @Value("${naver.storage.endPoint}")
    private String endPoint;

    @Value("${naver.storage.uploadPath}")
    private String uploadPath;

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
