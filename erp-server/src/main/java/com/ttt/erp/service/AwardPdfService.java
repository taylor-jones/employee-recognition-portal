package com.ttt.erp.service;


import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.ttt.erp.model.Award;
import com.ttt.erp.model.AwardType;
import com.ttt.erp.model.Employee;
import com.ttt.erp.model.UserAccount;
import com.ttt.erp.repository.AwardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


@Service
public class AwardPdfService {
    @Autowired
    private AwardRepository repository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SignatureService signatureService;


    // class-level constants
    private static final String OUTPUT_DIR = "awards/";
    private static final PdfNumber LANDSCAPE = new PdfNumber(90);


    private ByteArrayInputStream generateAwardPdf(Award award) throws IOException {
        // gather the components needed to build the file name
        Employee employee = award.getEmployee();
        UserAccount userAccount = award.getUserAccount();
        AwardType awardType = award.getAwardType();

        // setup the pdf document
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(output, new WriterProperties().setPdfVersion(PdfVersion.PDF_2_0));
        PdfDocument pdfDocument = new PdfDocument(writer);
        pdfDocument.setTagged();
        PageSize pageSize = PageSize.LETTER.rotate();
        pdfDocument.setDefaultPageSize(pageSize);
        Document document = new Document(pdfDocument);

        // add the TTT logo
        String logoSrc = "src/main/resources/logo.png";
        Image logo = new Image(ImageDataFactory.create(logoSrc)).scaleAbsolute(200, 50);
        document.add(logo);

        document.add(new Paragraph(awardType.getName()));
        document.add(new Paragraph("awarded to"));
        document.add(new Paragraph(employee.getFullName()));

        if (award.getDescription() != null) {
            document.add(new Paragraph(award.getDescription()));
        }

        if (award.getAwardedDate() != null) {
            document.add(new Paragraph("Awarded on "));
            document.add(new Paragraph(award.getAwardedDate().toString()));
        }

        if (award.getAwardedTime() != null) {
            document.add(new Paragraph(" at "));
            document.add(new Paragraph(award.getAwardedTime().toString()));
        }

        document.add(new Paragraph(" by "));
        document.add(new Paragraph(userAccount.getUsername()));

        if (userAccount.getSignature() != null) {
            Image signature = new Image(
                ImageDataFactory.create(
                    signatureService.getSignatureBytes(userAccount.getSignature())
                )
            ).scaleAbsolute(100, 100);
            document.add(signature);
        }

        document.close();
        return new ByteArrayInputStream(output.toByteArray());
    }


    public ResponseEntity<Long> sendAwardPdf(Long id) throws FileNotFoundException {
        try {
            // gather the components needed to build the file name
            Award award = repository.findById(id);
            Employee employee = award.getEmployee();
            UserAccount userAccount = award.getUserAccount();

            String filename = employee.getFullName() + " - Award.pdf";
            String subjectLine = "New Award from " + userAccount.getUsername() + " at TTT";
            String htmlString = "<p>Dear " + employee.getFirstName() + ",</p>" +
                "<p>Thank you for your contributions to our organization. " +
                "On behalf of everyone at Team Triple T, we'd like to present " +
                "you with this award as a small token of our appreciation.</p>" +
                "<p>Keep up the great work!</p>";

            // get the pdf data
            ByteArrayInputStream stream = generateAwardPdf(award);

            // send the pdf in an email to the recipient employee
            emailService.sendEmailWithAttachment(
                employee.getEmail(),
                subjectLine,
                htmlString,
                filename,
                stream
            );

            return ResponseEntity.ok().build();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

}


