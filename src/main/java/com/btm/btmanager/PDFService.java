package com.btm.btmanager;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.FileOutputStream;
import java.util.List;

public class PDFService {
    public static void exportToPdf(List<Task> tasks, String filepath) {
        try {
            PdfWriter writer = new PdfWriter(new FileOutputStream(filepath));
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            document.add(new Paragraph("Liste der Tätigkeiten").setBold().setFontSize(18));

            for (Task task : tasks) {
                String beschreibung = "Beschreibung: " + task.getBeschreibung();
                String startzeit = "Startzeit: " + task.getStartzeit().toString();
                String endzeit = "Endzeit: " + task.getEndzeit().toString();
                document.add(new Paragraph(beschreibung));
                document.add(new Paragraph(startzeit));
                document.add(new Paragraph(endzeit));
                document.add(new Paragraph("----------"));
            }

            document.close();
            System.out.println("PDF wurde erfolgreich erstellt und gespeichert unter: " + filepath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
