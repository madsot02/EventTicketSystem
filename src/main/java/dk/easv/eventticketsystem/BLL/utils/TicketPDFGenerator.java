package dk.easv.eventticketsystem.BLL.utils;

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.UnitValue;

import dk.easv.eventticketsystem.BE.Event;
import dk.easv.eventticketsystem.BE.Ticket;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class TicketPDFGenerator {

    public static void generateTicket(List<Ticket> tickets, Event event) throws Exception {

        String fileName = "tickets_" + tickets.get(0).getTicketUUID() + ".pdf";

        PdfWriter writer = new PdfWriter(fileName);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A5);

        for (int i = 0; i < tickets.size(); i++) {
            Ticket ticket = tickets.get(i);

            document.add(new Paragraph("EVENT TICKET")
                    .setBold()
                    .setFontSize(14));

            document.add(new Paragraph("-----------------------------"));

            document.add(new Paragraph("Event: " + event.getName()));
            document.add(new Paragraph("Location: " + event.getLocation()));
            document.add(new Paragraph("Date: " + event.getStartDate() + "  " + event.getStartTime()
                    + " - " + event.getEndDate() + "  " + event.getEndTime()));

            document.add(new Paragraph("-----------------------------"));

            document.add(new Paragraph("Type: " + ticket.getTypeName()));
            document.add(new Paragraph("Price: " + ticket.getPrice() + " DKK"));

            if (ticket.getCustomerName() != null && !ticket.getCustomerName().isEmpty()) {
                document.add(new Paragraph("Name: " + ticket.getCustomerName()));
                document.add(new Paragraph("Email: " + ticket.getCustomerEmail()));
            }

            // QR code centreret
            Image qrImage = generateQRCode(ticket.getTicketUUID());
            qrImage.setWidth(100);
            qrImage.setHeight(100);
            qrImage.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER);
            document.add(qrImage);

            // Barcode smallere og centreret under QR
            Image barcodeImage = generateBarcode1D(ticket.getTicketUUID());
            barcodeImage.setWidth(160);
            barcodeImage.setHeight(40);
            barcodeImage.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER);
            document.add(barcodeImage);

            document.add(new Paragraph("UUID: " + ticket.getTicketUUID())
                    .setFontSize(7)
                    .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER));

            if (i < tickets.size() - 1) {
                document.add(new AreaBreak());
            }
        }

        document.close();
    }

    private static Image generateQRCode(String text) throws Exception {
        int size = 140;
        BitMatrix matrix = new MultiFormatWriter()
                .encode(text, BarcodeFormat.QR_CODE, size, size);
        Path tempFile = File.createTempFile("qrc", ".png").toPath();
        MatrixToImageWriter.writeToPath(matrix, "PNG", tempFile);
        return new Image(com.itextpdf.io.image.ImageDataFactory.create(tempFile.toAbsolutePath().toString()));
    }

    private static Image generateBarcode1D(String text) throws Exception {
        int width = 220;
        int height = 60;
        BitMatrix matrix = new MultiFormatWriter()
                .encode(text, BarcodeFormat.CODE_128, width, height);
        File tempFile = File.createTempFile("barcode", ".png");
        MatrixToImageWriter.writeToPath(matrix, "PNG", tempFile.toPath());
        return new Image(com.itextpdf.io.image.ImageDataFactory.create(tempFile.getAbsolutePath()));
    }
}