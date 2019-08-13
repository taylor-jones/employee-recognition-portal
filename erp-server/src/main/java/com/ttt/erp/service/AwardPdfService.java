package com.ttt.erp.service;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
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
import java.time.format.DateTimeFormatter;


@Service
public class AwardPdfService {
    @Autowired
    private AwardRepository repository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SignatureService signatureService;


    // iText constants
    private static final PdfNumber LANDSCAPE = new PdfNumber(90);
    public static final int	ALIGN_CENTER = 3;
    public static final int	ALIGN_LEFT = 1;
    public static final int	ALIGN_RIGHT = 2;

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

        //
        // Define some styling
        //

        PdfFont timesBold = PdfFontFactory.createFont(FontConstants.TIMES_BOLD);
        PdfFont timesItalic = PdfFontFactory.createFont(FontConstants.TIMES_ITALIC);
        PdfFont timesRoman = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);

        Style h1 = new Style().setFontSize(44).setFont(timesBold).setTextAlignment(TextAlignment.CENTER);
        Style h2 = new Style().setFontSize(28).setFont(timesBold).setTextAlignment(TextAlignment.CENTER);
        Style h4 = new Style().setFontSize(14).setFont(timesRoman).setTextAlignment(TextAlignment.CENTER);
        Style p = new Style().setFontSize(14).setFont(timesItalic).setTextAlignment(TextAlignment.CENTER);
        Style spacer = new Style().setFontSize(90).setTextAlignment(TextAlignment.CENTER);

        // add the TTT logo
        String logoSrc = "src/main/resources/logo.png";
        Image logo = new Image(ImageDataFactory.create(logoSrc))
            .scaleAbsolute(168, 64)
            .setHorizontalAlignment(HorizontalAlignment.CENTER);
        document.add(logo);
        document.add(new Paragraph().addStyle(spacer));

        // add the Award Type & Employee
        document.add(new Paragraph(awardType.getName()).addStyle(h1));
        document.add(new Paragraph().addStyle(spacer));
        document.add(new Paragraph("This certificate is presented to").addStyle(p));
        document.add(new Paragraph(employee.getFullName()).addStyle(h2));
        document.add(new Paragraph().addStyle(spacer));

        // add the Award description
        if (award.getDescription() != null) {
            document.add(new Paragraph(award.getDescription()).addStyle(p));
        }

        // add the Award Date & Award Time
        if (award.getAwardedDate() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy");
            String formattedDate = formatter.format(award.getAwardedDate());

            document.add(new Paragraph().addStyle(h1));

            if (award.getAwardedTime() != null) {
                String formattedTime = award.getAwardedTime().format(DateTimeFormatter.ofPattern("h:mm a"));

                document.add(new Paragraph()
                    .add(new Text("Awarded on ").addStyle(p))
                    .add(formattedDate).addStyle(h4)
                    .add(new Text(" at ").addStyle(p))
                    .add(formattedTime).addStyle(h4)
                );
            } else {
                document.add(new Paragraph()
                    .add(new Text("Awarded on ").addStyle(p))
                    .add(formattedDate).addStyle(h4)
                );
            }
        }

        // Add the Award User
        document.add(new Paragraph());
        document.add(new Paragraph("Authorized by").addStyle(p));

        // Add the Award User's signature
        if (userAccount.getSignature() != null) {
            Image signature = new Image(
                ImageDataFactory.create(
                    signatureService.getSignatureBytes(userAccount.getSignature())
                )
            ).scaleAbsolute(75, 75).setHorizontalAlignment(HorizontalAlignment.CENTER);
            document.add(signature);
        }

        document.add(new Paragraph(userAccount.getUsername()).addStyle(h4));
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
            String htmlMessage = "<p>Dear " + employee.getFirstName() + ",</p>" +
                "<p>Thank you for your contributions to our organization. " +
                "On behalf of everyone at Team Triple T, we'd like to present " +
                "you with this award as a small token of our appreciation.</p>" +
                "<p>Keep up the great work!</p><br />" +
                "<p>Sincerely,</p><p>" + userAccount.getUsername() + "</p><br />";

            // get the pdf data
            ByteArrayInputStream stream = generateAwardPdf(award);

            // send the pdf in an email to the recipient employee
            emailService.sendEmailWithAttachment(
                employee.getEmail(),
                subjectLine,
                htmlMessage,
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



