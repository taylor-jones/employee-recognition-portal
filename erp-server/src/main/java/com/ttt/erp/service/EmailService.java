package com.ttt.erp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/*
* TR: I envision the award service either inheriting from this class or having its own
* private instance of this class. Whichever's the desired flavor.
*/  
@Service
public class EmailService {

  @Autowired private JavaMailSender javaMailSender;
  public EmailService() {}

  /*  @toAddress: fully qualified email address, ie: "rietzt@oregonstate.edu"
  *   @subjectLine: a string to be the subject of the email
  *   @htmlString: html markup as string (please place extreme limits on css)
  */
  public void sendEmail(String toAddress, String subjectLine, String htmlString) throws MailException, MessagingException, IOException {
    MimeMessage msg = javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(msg, true);
    helper.setTo(toAddress);
    helper.setSubject(subjectLine);
    helper.setText(htmlString, true);
    // TR: This line below will come in handy for our PDF attachments when we get to that :)
        //helper.addAttachment("somephoto.pdf", new ClassPathResource("anotherName.pdf"));
    javaMailSender.send(msg);
  }

}

