package za.ac.unisa.lms.tools.tracking.pdf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class PDFSample {

    // Page configuration
    private static final PDRectangle PAGE_SIZE = PDRectangle.A3;
    private static final float MARGIN = 20;
    private static final boolean IS_LANDSCAPE = true;

    // Font configuration
    private static final PDFont TEXT_FONT = PDType1Font.HELVETICA;
    private static final float FONT_SIZE = 10;

    // Table configuration
    private static final float ROW_HEIGHT = 15;
    private static final float CELL_MARGIN = 2;
    
    public static void main(String[] args) throws IOException {
        //new PDFTableGenerator().generatePDF(createContent());
    }

    private static PDFTable createContent() {
        // Total size of columns must not be greater than table width.
        List<PDFColumn> columns = new ArrayList<PDFColumn>();
        columns.add(new PDFColumn("FirstName", 90));
        columns.add(new PDFColumn("LastName", 90));
        columns.add(new PDFColumn("Email", 230));
        columns.add(new PDFColumn("ZipCode", 43));
        columns.add(new PDFColumn("MailOptIn", 50));
        columns.add(new PDFColumn("Code", 80));
        columns.add(new PDFColumn("Branch", 39));
        columns.add(new PDFColumn("Product", 300));
        columns.add(new PDFColumn("Date", 120));
        columns.add(new PDFColumn("Channel", 43));

        String[][] content = { 
                { "FirstName", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334", "yFone 5 XS", "31/05/2013 07:15 am", "WEB" },
                { "FirstName", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334", "yFone 5 XS", "31/05/2013 07:15 am", "WEB" },
                { "FirstName", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334", "yFone 5 XS", "31/05/2013 07:15 am", "WEB" }
        };

        float tableHeight = IS_LANDSCAPE ? PAGE_SIZE.getWidth() - (2 * MARGIN) : PAGE_SIZE.getHeight() - (2 * MARGIN);

        PDFTable table = new PDFTableBuilder()
            .setCellMargin(CELL_MARGIN)
            .setColumns(columns)
            .setContent(content)
            .setHeight(tableHeight)
            .setNumberOfRows(content.length)
            .setRowHeight(ROW_HEIGHT)
            .setMargin(MARGIN)
            .setPageSize(PAGE_SIZE)
            .setLandscape(IS_LANDSCAPE)
            .setTextFont(TEXT_FONT)
            .setFontSize(FONT_SIZE)
            .build();
        return table;
    }   
}
