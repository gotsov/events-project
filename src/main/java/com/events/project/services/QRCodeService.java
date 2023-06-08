package com.events.project.services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class QRCodeService {

    public byte[] generateQRCodeImage(String ticketData) throws WriterException, IOException {
        int width = 250;
        int height = 250;
        int margin = 0;

        String format = "png";

        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(ticketData, BarcodeFormat.QR_CODE, width, height, hints);

        int matrixWidth = bitMatrix.getWidth();
        int matrixHeight = bitMatrix.getHeight();

        int qrCodeWidth = matrixWidth - (2 * margin);
        int qrCodeHeight = matrixHeight - (2 * margin);

        BufferedImage qrCodeImage = new BufferedImage(qrCodeWidth, qrCodeHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = qrCodeImage.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, qrCodeWidth, qrCodeHeight);
        graphics.setColor(Color.BLACK);

        for (int x = margin; x < matrixWidth - margin; x++) {
            for (int y = margin; y < matrixHeight - margin; y++) {
                if (bitMatrix.get(x, y)) {
                    graphics.fillRect(x - margin, y - margin, 1, 1);
                }
            }
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(qrCodeImage, format, outputStream);

        return outputStream.toByteArray();
    }
}
