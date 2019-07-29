package za.ac.unisa.lms.tools.tracking.pdf;

import java.util.List;

import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;

public class PDFTableBuilder {

    private PDFTable table = new PDFTable();

    public PDFTableBuilder setHeight(float height) {
        table.setHeight(height);
        return this;
    }

    public PDFTableBuilder setNumberOfRows(Integer numberOfRows) {
        table.setNumberOfRows(numberOfRows);
        return this;
    }
    
    public PDFTableBuilder setNumberOfRowsDct(Integer numberOfRowsDct) {
        table.setNumberOfRowsDct(numberOfRowsDct);
        return this;
    }
    
    public PDFTableBuilder setNumberOfRowsStuAss(Integer numberOfRowsStuAss) {
        table.setNumberOfRowsStuAss(numberOfRowsStuAss);
        return this;
    }

    public PDFTableBuilder setRowHeight(float rowHeight) {
        table.setRowHeight(rowHeight);
        return this;
    }
    
    public PDFTableBuilder setColNameRowHeight(float colNameRowHeight) {
        table.setColNameRowHeight(colNameRowHeight);
        return this;
    }

    public PDFTableBuilder setContent(String[][] content) {
        table.setContent(content);
        return this;
    }

    public PDFTableBuilder setColumns(List<PDFColumn> columns) {
        table.setColumns(columns);
        return this;
    }

    public PDFTableBuilder setColumnsDct(List<PDFColumn> columnsDct) {
        table.setColumnsDct(columnsDct);
        return this;
    }
    
    public PDFTableBuilder setColumnsStuAss(List<PDFColumn> columnsStuAss) {
        table.setColumnsStuAss(columnsStuAss);
        return this;
    }
    
    public PDFTableBuilder setCellMargin(float cellMargin) {
        table.setCellMargin(cellMargin);
        return this;
    }

    public PDFTableBuilder setMargin(float margin) {
        table.setMargin(margin);
        return this;
    }

    public PDFTableBuilder setPageSize(PDRectangle pageSize) {
        table.setPageSize(pageSize);
        return this;
    }

    public PDFTableBuilder setLandscape(boolean landscape) {
        table.setLandscape(landscape);
        return this;
    }

    public PDFTableBuilder setColNameTextFont(PDFont colNameTextFont) {
        table.setColNameTextFont(colNameTextFont);
        return this;
    }
    
    public PDFTableBuilder setTextFont(PDFont textFont) {
        table.setTextFont(textFont);
        return this;
    }

    public PDFTableBuilder setColNameFontSize(float colNameFontSize) {
        table.setColNameFontSize(colNameFontSize);
        return this;
    }

    public PDFTableBuilder setFontSize(float fontSize) {
        table.setFontSize(fontSize);
        return this;
    }
    
    public PDFTable build() {
        return table;
    }
}
