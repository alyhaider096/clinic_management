/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package printer;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class PrescriptionPrinter {

    public static void print(Map<String, String> patientData) {
        try {
            // Generate filename
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String filename = "Prescription_" + patientData.get("name").replaceAll("\\s+", "_") + "_" + timeStamp + ".pdf";

            // Output path on Desktop
            String desktopPath = System.getProperty("user.home") + File.separator + "Desktop";
            File outputDir = new File(desktopPath);
            if (!outputDir.exists()) outputDir.mkdirs();

            String outputPath = desktopPath + File.separator + filename;
            File pdfFile = new File(outputPath);

            // Create PDF
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            document.open();

            // Fonts
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Font subTitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            Font prescriptionTitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);

            // Header Line 1: Doctor Title
            Paragraph doctorTitle = new Paragraph("Child Specialist Dr Zubair Iqbal", headerFont);
            doctorTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(doctorTitle);

            // Header Line 2: Prescription Slip Title
            Paragraph slipTitle = new Paragraph("Prescription Slip", titleFont);
            slipTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(slipTitle);

            // Address
            Paragraph address = new Paragraph("Hospital road,behind PSO and bonfire, Rahim Yar Khan", normalFont);
            address.setAlignment(Element.ALIGN_CENTER);
            document.add(address);

            document.add(Chunk.NEWLINE);

            // Patient Info Table
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            addTableRow(table, "Patient Name:", patientData.get("name"), subTitleFont, normalFont);
            addTableRow(table, "Phone:", patientData.get("phone"), subTitleFont, normalFont);
            addTableRow(table, "Age:", patientData.get("age"), subTitleFont, normalFont);
            addTableRow(table, "Weight:", patientData.get("weight"), subTitleFont, normalFont);
            addTableRow(table, "Visit Date:", patientData.get("visitDate"), subTitleFont, normalFont);
            addTableRow(table, "Follow-Up Date:", patientData.get("followUpDate"), subTitleFont, normalFont);
            document.add(table);
            document.add(Chunk.NEWLINE);

            // Symptoms Section
            document.add(new Paragraph("Symptoms:", subTitleFont));
            document.add(new Paragraph(patientData.get("symptoms"), normalFont));
            document.add(Chunk.NEWLINE);

            // Prescription Heading
            Paragraph presTitle = new Paragraph("Prescription:", prescriptionTitleFont);
            document.add(presTitle);

            // 9-line gap after "Prescription:"
            for (int i = 0; i < 9; i++) {
                document.add(Chunk.NEWLINE);
            }

            // Prescription content
            document.add(new Paragraph(patientData.get("prescription"), normalFont));
            document.add(Chunk.NEWLINE);

            // Facebook & timestamp (bottom-left)
            document.add(new Paragraph("Facebook: fb.com/yourclinic", normalFont));
            document.add(new Paragraph("Generated: " + new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date()), normalFont));

            // Signature (far right)
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

            PdfPTable signatureTable = new PdfPTable(1);
            signatureTable.setWidthPercentage(40);
            signatureTable.setHorizontalAlignment(Element.ALIGN_RIGHT);

            PdfPCell lineCell = new PdfPCell(new Phrase("_________________________", normalFont));
            lineCell.setBorder(Rectangle.NO_BORDER);
            lineCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            signatureTable.addCell(lineCell);

            PdfPCell labelCell = new PdfPCell(new Phrase("Doctor's Signature", normalFont));
            labelCell.setBorder(Rectangle.NO_BORDER);
            labelCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            signatureTable.addCell(labelCell);

            document.add(signatureTable);

            // Finish
            document.close();
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(pdfFile);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addTableRow(PdfPTable table, String label, String value, Font labelFont, Font valueFont) {
        table.addCell(getCell(label, labelFont));
        table.addCell(getCell(value, valueFont));
    }

    private static PdfPCell getCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(5);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }
}
