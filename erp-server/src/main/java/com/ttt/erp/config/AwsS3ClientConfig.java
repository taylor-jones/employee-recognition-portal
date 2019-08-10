package com.ttt.erp.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsS3ClientConfig {

  @Value("${aws.accessKey}")
  private String accessKey;

  @Value("${aws.keyId}")
  private String secretKey; 

  @Value("${aws.region}")
  private String regionName;

  @Value("${aws.bucket.name}")
  private String bucketName;

  AwsS3ClientConfig() {

  }

  @Bean()
  public AmazonS3 AwsS3Client() {
    AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
    AmazonS3 AwsS3Client = AmazonS3ClientBuilder
      .standard()
      .withCredentials(new AWSStaticCredentialsProvider(credentials))
      .withRegion(Regions.US_WEST_2)
      .build();
    return AwsS3Client;
  }

  String getBucketName() {
    return this.bucketName;
  }

  String getRegionName() {
    return this.regionName;
  }

}