package com.fitzone.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

public class QrCodeService {
    private static final Logger logger = LoggerFactory.getLogger(QrCodeService.class);

    public static BufferedImage generateQrCodeBufferedImage(String text, int width, int height) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
            return MatrixToImageWriter.toBufferedImage(bitMatrix);
        } catch (Exception e) {
            logger.error("Failed to generate QR Code image", e);
            return null;
        }
    }

    public static byte[] generateQrCodeByteArray(String text, int width, int height) {
        try {
            BufferedImage bufferedImage = generateQrCodeBufferedImage(text, width, height);
            if (bufferedImage == null) return new byte[0];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "PNG", baos);
            return baos.toByteArray();
        } catch (Exception e) {
            logger.error("Failed to convert QR Code to byte array", e);
            return new byte[0];
        }
    }
}
