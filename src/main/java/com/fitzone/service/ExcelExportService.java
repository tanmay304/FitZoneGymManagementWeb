package com.fitzone.service;

import com.fitzone.model.Attendance;
import com.fitzone.model.Member;
import com.fitzone.model.Payment;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelExportService {
    private static final Logger logger = LoggerFactory.getLogger(ExcelExportService.class);

    public static boolean exportMembersToExcel(List<Member> members, File destFile) {
        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream fos = new FileOutputStream(destFile)) {

            Sheet sheet = workbook.createSheet("Members");
            Row headerRow = sheet.createRow(0);
            String[] columns = {"ID", "First Name", "Last Name", "Email", "Mobile", "State", "City", "Address", "Created Date"};

            CellStyle headerStyle = createHeaderStyle(workbook);

            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            int rowIdx = 1;
            for (Member m : members) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(m.getId());
                row.createCell(1).setCellValue(m.getFname() != null ? m.getFname() : "");
                row.createCell(2).setCellValue(m.getLname() != null ? m.getLname() : "");
                row.createCell(3).setCellValue(m.getEmail() != null ? m.getEmail() : "");
                row.createCell(4).setCellValue(m.getMobile() != null ? m.getMobile() : "");
                row.createCell(5).setCellValue(m.getState() != null ? m.getState() : "");
                row.createCell(6).setCellValue(m.getCity() != null ? m.getCity() : "");
                row.createCell(7).setCellValue(m.getAddress() != null ? m.getAddress() : "");
                row.createCell(8).setCellValue(m.getCreateDate() != null ? m.getCreateDate().toString() : "");
            }

            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(fos);
            return true;
        } catch (Exception e) {
            logger.error("Failed to export members to Excel", e);
            return false;
        }
    }

    public static boolean exportPaymentsToExcel(List<Payment> payments, File destFile) {
        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream fos = new FileOutputStream(destFile)) {

            Sheet sheet = workbook.createSheet("Payments");
            Row headerRow = sheet.createRow(0);
            String[] columns = {"ID", "Booking ID", "Member Name", "Package", "Payment Type", "Method", "Status", "Amount (₹)", "Payment Date"};

            CellStyle headerStyle = createHeaderStyle(workbook);

            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            int rowIdx = 1;
            for (Payment p : payments) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(p.getId());
                row.createCell(1).setCellValue(p.getBookingID() != null ? p.getBookingID() : "");
                row.createCell(2).setCellValue(p.getMemberName() != null ? p.getMemberName() : "");
                row.createCell(3).setCellValue(p.getPackageName() != null ? p.getPackageName() : "");
                row.createCell(4).setCellValue(p.getPaymentType() != null ? p.getPaymentType() : "");
                row.createCell(5).setCellValue(p.getPaymentMethod() != null ? p.getPaymentMethod() : "Cash");
                row.createCell(6).setCellValue(p.getStatus() != null ? p.getStatus() : "Paid");
                row.createCell(7).setCellValue(p.getPayment() != null ? p.getPayment() : "0");
                row.createCell(8).setCellValue(p.getPaymentDate() != null ? p.getPaymentDate().toString() : "");
            }

            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(fos);
            return true;
        } catch (Exception e) {
            logger.error("Failed to export payments to Excel", e);
            return false;
        }
    }

    public static boolean exportAttendanceToExcel(List<Attendance> attendanceList, File destFile) {
        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream fos = new FileOutputStream(destFile)) {

            Sheet sheet = workbook.createSheet("Attendance");
            Row headerRow = sheet.createRow(0);
            String[] columns = {"ID", "User ID", "Member Name", "Check-In", "Check-Out", "Date", "Status", "Method"};

            CellStyle headerStyle = createHeaderStyle(workbook);

            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            int rowIdx = 1;
            for (Attendance a : attendanceList) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(a.getId());
                row.createCell(1).setCellValue(a.getUserId());
                row.createCell(2).setCellValue(a.getMemberName() != null ? a.getMemberName() : "");
                row.createCell(3).setCellValue(a.getCheckIn() != null ? a.getCheckIn().toString() : "");
                row.createCell(4).setCellValue(a.getCheckOut() != null ? a.getCheckOut().toString() : "");
                row.createCell(5).setCellValue(a.getAttendanceDate() != null ? a.getAttendanceDate().toString() : "");
                row.createCell(6).setCellValue(a.getStatus() != null ? a.getStatus() : "");
                row.createCell(7).setCellValue(a.getMethod() != null ? a.getMethod() : "");
            }

            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(fos);
            return true;
        } catch (Exception e) {
            logger.error("Failed to export attendance to Excel", e);
            return false;
        }
    }

    private static CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }
}
