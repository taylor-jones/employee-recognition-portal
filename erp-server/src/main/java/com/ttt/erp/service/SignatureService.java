package com.ttt.erp.service;

import org.springframework.stereotype.Service;

import com.ttt.erp.model.UserAccount;
import com.ttt.erp.service.UserAccountService;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.File;
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

  private String directoryName = "signatures";

  //parses jpg from: 'data:image/jpg;base64,ADkadjfkajdflKJSDFlkfjdafksfj...='
  private String imgTypeFromBase64(String encoded) {
    return encoded.split(";base64,")[0].split("/")[1];
  }

  private byte[] base64ToBytes(String encodedImage) {
    return Base64.getDecoder().decode( encodedImage );                                        // rawData[1] is the base64 data
  }

  private String dataFromBase64(String base64) {
    return base64.split(";base64,")[1];
  }

  private String bytesToBase64(byte[] bytes) {
    return Base64.getEncoder().encodeToString(bytes);
  }

  private void createSignatureDirIfNotExists() {
    File directory = new File(directoryName);
    if (! directory.exists()){
        directory.mkdir();
    }
  }

  public String getSignature(String fName) {
    try {
      byte[] encoded = FileUtils.readFileToByteArray(new File(directoryName + "/" + fName));
      return bytesToBase64(encoded);
    } catch (Exception e) {
      return e.getStackTrace().toString();
    }
  }

  public Optional<String> getSignatureForUsername(String username) {
    return Optional.ofNullable(this.userService.signatureFileNameByUsername(username));
  }

  public String newSignatureForUser(String body, UserAccount user) {
    byte[] bytes = base64ToBytes(dataFromBase64(body));
    try {
        createSignatureDirIfNotExists();
        String fName = UUID.randomUUID() + "." + imgTypeFromBase64(body);
        FileUtils.writeByteArrayToFile (
          new File(directoryName + "/" + fName),
          bytes
        );
        user.setSignature(fName);
        return fName;
    } catch (Exception e) {
        // send stack trace on fail: should be rethought a bit for security
        return e.getStackTrace().toString();
    }
  }

}