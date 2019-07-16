package com.ttt.erp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Base64;
import java.util.UUID;

@RestController
@RequestMapping("/api/signatures")
public class SignatureController {

    private String directoryName = "signatures";

    private void createSignatureDirIfNotExists() {
      File directory = new File(directoryName);
      if (! directory.exists()){
          directory.mkdir();
      }
    }

    @PostMapping("")
    public ResponseEntity<String> createSignature(@RequestBody Map<String, String> body) {
        String[] rawData = body.get("signature").split(";base64,");
        byte[] bytes = Base64.getDecoder().decode(rawData[1]);                                // rawData[1] is the base64 data
        try {
            createSignatureDirIfNotExists();
            File imageFile = new File(directoryName + "/" + UUID.randomUUID() + "." + rawData[0].split("/")[1]);
            FileOutputStream fileStream = new FileOutputStream(imageFile);
            fileStream.write(bytes);                                                          // write decoded bytes to file
            fileStream.flush();
            fileStream.close();
            return new ResponseEntity<String> ("Success", HttpStatus.OK);                     // send short success message on success
        } catch (Exception e) {
            // send stack trace on fail: should be rethought a bit for security
            return new ResponseEntity<String> (e.getStackTrace().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}