package ru.clevertec.test.service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.test.repository.entity.product.Product;
import ru.clevertec.test.service.exception.IncorrectInput;
import ru.clevertec.test.service.impl.checks.SimpleCheckService;
import ru.clevertec.test.service.interfaces.PrintPdfService;

import java.io.IOException;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class PrintPdfServiceImpl implements PrintPdfService {

    private final SimpleCheckService simpleCheck;

    public void printPdf() {
        Document document = new Document();
        Path file = getPathForReceipt();
        PdfWriter writer = getPdfWriter(document, file);
        PdfReader reader = getPdfReader();
        document.open();
        addPageWithImage(document, writer, reader);
        addDiv(document);
        addHeader(document);
        addTableWithProduct(document);
        summaryTable();
        document.close();
    }

    private PdfReader getPdfReader() {
        PdfReader reader = null;
        try {
            reader = new PdfReader("src/main/resources/pdf/Clevertec_Template.pdf");
        } catch (IOException e) {
            throw new IncorrectInput();
        }
        return reader;
    }

    private PdfWriter getPdfWriter(Document document, Path file) {
        PdfWriter writer = null;
        try {
            writer = PdfWriter.getInstance(document, Files.newOutputStream(file));
        } catch (DocumentException | IOException e) {
            throw new IncorrectInput();
        }
        return writer;
    }

    private void summaryTable() {
        PdfPTable pdfPTable = new PdfPTable(2);
        pdfPTable.addCell(new PdfPCell(Phrase.getInstance("Total without discount"))).setHorizontalAlignment(Element.ALIGN_LEFT);
        pdfPTable.addCell(new PdfPCell(Phrase.getInstance(String.valueOf(simpleCheck.generalСost()
                .setScale(2, RoundingMode.CEILING))))).setHorizontalAlignment(Element.ALIGN_RIGHT);
        pdfPTable.addCell(new PdfPCell(Phrase.getInstance("Discount"))).setHorizontalAlignment(Element.ALIGN_LEFT);
        pdfPTable.addCell(new PdfPCell(Phrase.getInstance(String.valueOf(simpleCheck.discountAmount()
                .setScale(2, RoundingMode.CEILING))))).setHorizontalAlignment(Element.ALIGN_RIGHT);
        pdfPTable.addCell(new PdfPCell(Phrase.getInstance("Discount"))).setHorizontalAlignment(Element.ALIGN_LEFT);
        pdfPTable.addCell(new PdfPCell(Phrase.getInstance(String.valueOf(simpleCheck.costWithDiscount()
                .setScale(2, RoundingMode.CEILING))))).setHorizontalAlignment(Element.ALIGN_RIGHT);
    }

    private void addTableWithProduct(Document document) {
        Map<Product, Integer> map = simpleCheck.getShoppingСart();
        PdfPTable pdfPTable = new PdfPTable(4);
        pdfPTable.addCell(new PdfPCell(Phrase.getInstance("quantity"))).setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(new PdfPCell(Phrase.getInstance("description"))).setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(new PdfPCell(Phrase.getInstance("price"))).setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(new PdfPCell(Phrase.getInstance("total price"))).setHorizontalAlignment(Element.ALIGN_CENTER);
        map.entrySet().stream()
                .peek(set -> pdfPTable.addCell(new PdfPCell(Phrase.getInstance(String.valueOf(set.getValue())))).setBorder(Rectangle.BOTTOM))
                .peek(set -> pdfPTable.addCell(new PdfPCell(Phrase.getInstance(set.getKey().getDescription()))).setBorder(Rectangle.BOTTOM))
                .peek(set -> pdfPTable.addCell(new PdfPCell(Phrase.getInstance(String.valueOf(set.getKey().getPrice())))).setBorder(Rectangle.BOTTOM))
                .peek(set -> pdfPTable.addCell(new PdfPCell(Phrase.getInstance(String
                        .valueOf(simpleCheck.nominationCost(set.getKey(), set.getValue()))))).setBorder(Rectangle.BOTTOM))
                .forEach(System.out::println);
        try {
            document.add(pdfPTable);
        } catch (DocumentException e) {
            throw new RuntimeException();
        }
    }

    private void addDiv(Document document) {
        PdfDiv pdfDiv = new PdfDiv();
        pdfDiv.setHeight(250f);
        try {
            document.add(pdfDiv);
        } catch (DocumentException e) {
            throw new IncorrectInput();
        }
    }

    private void addHeader(Document document) {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now().truncatedTo(ChronoUnit.SECONDS);
        Paragraph cashReceipt = new Paragraph(
                "CASH RECEIPT" +
                        "\n" + "Prostore" +
                        "\n" + "Kosmonavtov 34" +
                        "\n" + "Phone: +375 25 478-89-40" +
                        "\n" + "Date: " + date +
                        "\n" + "Time: " + time);
        cashReceipt.setAlignment(Element.ALIGN_CENTER);
        try {
            document.add(cashReceipt);
        } catch (DocumentException e) {
            throw new IncorrectInput();
        }
    }

    private void addPageWithImage(Document document, PdfWriter writer, PdfReader reader) {
        PdfImportedPage importedPage = writer.getImportedPage(reader, 1);
        PdfContentByte pdfContentByte = writer.getDirectContent();
        document.newPage();
        pdfContentByte.addTemplate(importedPage, 0, 0);
    }

    private Path getPathForReceipt() {
        long l = ZonedDateTime.now().toInstant().toEpochMilli();
        Path file = null;
        try {
            file = Files.createFile(Path.of("src", "main", "resources", "pdf", String.valueOf(l) + ".pdf"));
        } catch (IOException e) {
            throw new IncorrectInput();
        }
        return file;
    }
}
