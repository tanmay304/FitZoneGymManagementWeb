package com.fitzone.service;

import com.fitzone.model.Member;
import com.fitzone.model.Payment;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PdfService {
    private static final Logger logger = LoggerFactory.getLogger(PdfService.class);

    public static boolean generatePaymentReceiptPdf(Payment payment, File destFile) {
        Document document = new Document(PageSize.A4, 36, 36, 36, 36);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(destFile));
            document.open();

            // Colors
            BaseColor primaryColor = new BaseColor(41, 128, 185); // Blue Accent
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, primaryColor);
            Font subHeaderFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.DARK_GRAY);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 11, BaseColor.BLACK);
            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, BaseColor.BLACK);

            // Title
            Paragraph title = new Paragraph("FITZONE GYM MANAGEMENT", headerFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            Paragraph subtitle = new Paragraph("OFFICIAL PAYMENT RECEIPT & INVOICE", subHeaderFont);
            subtitle.setAlignment(Element.ALIGN_CENTER);
            subtitle.setSpacingAfter(20);
            document.add(subtitle);

            // Invoice details table
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1, 1});

            String receiptNo = payment.getReceiptNo() != null ? payment.getReceiptNo() : "REC-" + payment.getId();
            String txnId = payment.getTransactionId() != null ? payment.getTransactionId() : "TXN-" + System.currentTimeMillis();
            String dateStr = payment.getPaymentDate() != null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(payment.getPaymentDate()) : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            table.addCell(createCell("Receipt No: " + receiptNo, boldFont, false));
            table.addCell(createCell("Date: " + dateStr, normalFont, false));
            table.addCell(createCell("Transaction ID: " + txnId, normalFont, false));
            table.addCell(createCell("Payment Status: " + (payment.getStatus() != null ? payment.getStatus() : "PAID"), boldFont, false));

            table.setSpacingAfter(15);
            document.add(table);

            // Member & Package Breakdown Table
            PdfPTable detailsTable = new PdfPTable(2);
            detailsTable.setWidthPercentage(100);
            detailsTable.setWidths(new float[]{2, 3});

            addTableRow(detailsTable, "Member Name", payment.getMemberName() != null ? payment.getMemberName() : "N/A", boldFont, normalFont);
            addTableRow(detailsTable, "Package Name", payment.getPackageName() != null ? payment.getPackageName() : "N/A", boldFont, normalFont);
            addTableRow(detailsTable, "Payment Type", payment.getPaymentType() != null ? payment.getPaymentType() : "N/A", boldFont, normalFont);
            addTableRow(detailsTable, "Payment Method", payment.getPaymentMethod() != null ? payment.getPaymentMethod() : "Cash", boldFont, normalFont);
            addTableRow(detailsTable, "Amount Paid", "₹ " + payment.getPayment(), boldFont, boldFont);
            addTableRow(detailsTable, "GST (18% Included)", "₹ " + String.format("%.2f", parseDouble(payment.getPayment()) * 0.18), boldFont, normalFont);

            detailsTable.setSpacingAfter(20);
            document.add(detailsTable);

            // Add QR Code verification
            byte[] qrBytes = QrCodeService.generateQrCodeByteArray("Receipt: " + receiptNo + " | Txn: " + txnId + " | Amt: " + payment.getPayment(), 120, 120);
            if (qrBytes.length > 0) {
                com.itextpdf.text.Image qrImg = com.itextpdf.text.Image.getInstance(qrBytes);
                qrImg.setAlignment(Element.ALIGN_CENTER);
                document.add(qrImg);
            }

            Paragraph footer = new Paragraph("Thank you for training with FitZone Gym!\nFor queries, contact support@fitzonegym.com", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.ITALIC, BaseColor.GRAY));
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingBefore(15);
            document.add(footer);

            document.close();
            return true;
        } catch (Exception e) {
            logger.error("Failed to generate payment receipt PDF", e);
            if (document.isOpen()) document.close();
            return false;
        }
    }

    public static boolean generateMemberCardPdf(Member member, File destFile) {
        Document document = new Document(PageSize.A6.rotate(), 20, 20, 20, 20);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(destFile));
            document.open();

            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, new BaseColor(41, 128, 185));
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK);
            Font textFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.DARK_GRAY);

            Paragraph title = new Paragraph("FITZONE GYM MEMBER CARD", headerFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(10);
            document.add(title);

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{3, 2});

            PdfPCell infoCell = new PdfPCell();
            infoCell.setBorder(Rectangle.NO_BORDER);
            infoCell.addElement(new Paragraph("Member ID: #" + member.getId(), titleFont));
            infoCell.addElement(new Paragraph("Name: " + member.getFullName(), textFont));
            infoCell.addElement(new Paragraph("Email: " + member.getEmail(), textFont));
            infoCell.addElement(new Paragraph("Mobile: " + member.getMobile(), textFont));
            infoCell.addElement(new Paragraph("City: " + (member.getCity() != null ? member.getCity() : "N/A"), textFont));
            table.addCell(infoCell);

            byte[] qrBytes = QrCodeService.generateQrCodeByteArray("FITZONE-MEMBER-" + member.getId(), 100, 100);
            PdfPCell qrCell = new PdfPCell();
            qrCell.setBorder(Rectangle.NO_BORDER);
            qrCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            if (qrBytes.length > 0) {
                com.itextpdf.text.Image qrImg = com.itextpdf.text.Image.getInstance(qrBytes);
                qrCell.addElement(qrImg);
            }
            table.addCell(qrCell);

            document.add(table);
            document.close();
            return true;
        } catch (Exception e) {
            logger.error("Failed to generate member card PDF", e);
            if (document.isOpen()) document.close();
            return false;
        }
    }

    private static PdfPCell createCell(String text, Font font, boolean border) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        if (!border) cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(4);
        return cell;
    }

    private static void addTableRow(PdfPTable table, String label, String value, Font labelFont, Font valFont) {
        table.addCell(new PdfPCell(new Phrase(label, labelFont)));
        table.addCell(new PdfPCell(new Phrase(value, valFont)));
    }

    private static double parseDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
            return 0.0;
        }
    }
}
