package com.ttt.erp.service;

import org.springframework.stereotype.Service;

import com.ttt.erp.model.UserAccount;
import com.ttt.erp.service.UserAccountService;
import com.itextpdf.layout.element.Image;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.Base64;
import java.util.UUID;

/*
* TR: This is meant to be extended by other service classes that should get logged.
*/

@Service
public class SignatureService {
  
  @Autowired
  private UserAccountService userService;
  
  @Autowired
  private AwsS3ClientService AwsS3Client;

  private String directoryName = "signatures";

  //parses jpg from: 'data:image/jpg;base64,ADkadjfkajdflKJSDFlkfjdafksfj...='
  private String imgTypeFromBase64(String encoded) {
    return encoded.split(";base64,")[0].split("/")[1];
  }

  private byte[] base64ToBytes(String encodedImage) {
    return Base64.getDecoder().decode( encodedImage );
  }

  private String dataFromBase64(String base64) {
    return base64.split(";base64,")[1];
  }

  private String bytesToBase64(byte[] bytes) {
    return Base64.getEncoder().encodeToString(bytes);
  }

  private void createSignatureDirIfNotExists() {
    File directory = new File(this.directoryName);
    if (! directory.exists()){
      directory.mkdir();
    }
  }

  public String getSignatureBase64(String fName) {
    try {
      return bytesToBase64(getSignatureBytes(fName));
    } catch (Exception e) {
      return e.getStackTrace().toString();
    }
  }

  public byte[] getSignatureBytes(String fName) throws IOException {
    File f = this.AwsS3Client.getFile(directoryName + "/" + fName);
    byte[] encoded = FileUtils.readFileToByteArray(new File(directoryName + "/" + fName));
    f.delete();
    return encoded;
  }

  public Optional<String> getSignatureForUsername(String username) {
    return this.userService.signatureFileNameByUsername(username);
  }

  public void deleteSignatureByFileName(String fileName) {
    this.AwsS3Client.deleteFile(directoryName + "/" + fileName);
  }

  public String newSignatureForUser(String body, UserAccount user) {
    byte[] bytes = base64ToBytes(dataFromBase64(body));
    try {
        createSignatureDirIfNotExists();
        String fName = UUID.randomUUID() + "." + imgTypeFromBase64(body);
        File toWrite = new File(directoryName + "/" + fName);
        FileUtils.writeByteArrayToFile(toWrite, bytes);
        AwsS3Client.uploadFile(directoryName + "/" + fName, toWrite);
        user.setSignature(fName);
        toWrite.delete();
        return fName;
    } catch (Exception e) {
        return e.getStackTrace().toString();
    }
  }

}