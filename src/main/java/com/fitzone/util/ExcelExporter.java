package com.fitzone.util;

import com.fitzone.model.Payment;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.util.List;

public class ExcelExporter {

    public static boolean exportPaymentsToExcel(List<Payment> payments, String filePath) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Payments Report");

            // Header Row
            Row headerRow = sheet.createRow(0);
            String[] columns = {"ID", "Booking ID", "Member Name", "Package Name", "Payment Type", "Amount (₹)", "Payment Date"};

            CellStyle headerStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            headerStyle.setFont(font);

            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            int rowIdx = 1;
            for (Payment p : payments) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(p.getId());
                row.createCell(1).setCellValue(p.getBookingID());
                row.createCell(2).setCellValue(p.getMemberName() != null ? p.getMemberName() : "");
                row.createCell(3).setCellValue(p.getPackageName() != null ? p.getPackageName() : "");
                row.createCell(4).setCellValue(p.getPaymentType() != null ? p.getPaymentType() : "");
                row.createCell(5).setCellValue(p.getPayment() != null ? p.getPayment() : "0");
                row.createCell(6).setCellValue(p.getPaymentDate() != null ? p.getPaymentDate().toString() : "");
            }

            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
