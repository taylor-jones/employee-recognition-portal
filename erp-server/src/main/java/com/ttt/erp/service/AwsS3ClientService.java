package com.ttt.erp.service;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AwsS3ClientService {

  @Value("${aws.bucket.name}")
  private String bucketName;

  @Autowired
  private AmazonS3 AwsS3Client;

  void deleteFile(String fileName) {
    try {
      this.AwsS3Client.deleteObject(this.bucketName, fileName);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  Optional<PutObjectResult> uploadFile(String fileName, File file) {
    try {
      return Optional.ofNullable(
        this.AwsS3Client.putObject(this.bucketName, fileName, file)
      );
    } catch (Exception e) {
      e.printStackTrace();
      return Optional.empty();
    }
  }

  public File getFile(String filePath) throws IOException {
      S3Object s3object = this.AwsS3Client.getObject(this.bucketName, filePath);
      S3ObjectInputStream inputStream = s3object.getObjectContent();
      File toGet = new File(filePath);
      FileUtils.copyInputStreamToFile(inputStream, toGet);
      return toGet;
  }
  
}