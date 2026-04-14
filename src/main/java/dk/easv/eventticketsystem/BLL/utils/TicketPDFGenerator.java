package dk.easv.eventticketsystem.BLL.utils;

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import com.itextpdf.kernel.colors.ColorConstants;
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
        Document document = new Document(pdf, PageSize.A6);

        for (Ticket ticket : tickets) {

            document.add(new Paragraph("EVENT TICKET")
                    .setBold()
                    .setFontSize(14));

            document.add(new Paragraph("-----------------------------"));

            document.add(new Paragraph("Event: " + event.getName()));

            document.add(new Paragraph("Price: " + ticket.getPrice() + " DKK"));
            document.add(new Paragraph("Type: " + ticket.getTypeName()));

            if (ticket.getCustomerName() != null && !ticket.getCustomerName().isEmpty()) {
                document.add(new Paragraph("Name: " + ticket.getCustomerName()));
                document.add(new Paragraph("Email: " + ticket.getCustomerEmail()));
            }

            Image qrImage = generateQRCode(ticket.getTicketUUID());
            Image barcodeImage = generateBarcode1D(ticket.getTicketUUID());

            Table table = new Table(2);
            table.setWidth(UnitValue.createPercentValue(100));

            table.addCell(new Cell().add(qrImage).setBorder(null));
            table.addCell(new Cell().add(barcodeImage).setBorder(null));

            document.add(table);

            document.add(new Paragraph("UUID: " + ticket.getTicketUUID())
                    .setFontSize(8));

            document.add(new AreaBreak());
        }

        document.close();
    }

    // QR generator
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