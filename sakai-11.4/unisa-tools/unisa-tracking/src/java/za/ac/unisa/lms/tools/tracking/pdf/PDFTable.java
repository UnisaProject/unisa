package za.ac.unisa.lms.tools.tracking.pdf;

import java.util.List;

import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;

public class PDFTable {

    // Table attributes
    private float margin;
    private float height;
    private PDRectangle pageSize;
    private boolean isLandscape;
    private float colNameRowHeight;
    private float rowHeight;

    // font attributes
    private PDFont colNameTextFont;
    private float colNameFontSize;
    private PDFont textFont;
    private float fontSize;

    // Content attributes
    private Integer numberOfRows;
    private Integer numberOfRowsDct;
    private Integer numberOfRowsStuAss;
    private List<PDFColumn> columns;
    private List<PDFColumn> columnsDct;
    private List<PDFColumn> columnsStuAss;
    private String[][] content;
    private float cellMargin;
    
    public PDFTable() {
    }

    public Integer getNumberOfColumns() {
    	//log.debug("PDFTable - getNumberOfColumns="+getColumns().size());
        return this.getColumns().size();
    }

    public Integer getNumberOfColumnsDct() {
    	////log.debug("PDFTable - getNumberOfColumnsDct="+getColumnsDct().size());
        return this.getColumnsDct().size();
    }
    
    public Integer getNumberOfColumnsStuAss() {
    	////log.debug("PDFTable - getNumberOfColumnsStuAss="+getColumnsStuAss().size());
        return this.getColumnsStuAss().size();
    }
    
    public float getWidth() {
        float tableWidth = 0f;
        for (PDFColumn column : columns) {
            tableWidth += column.getWidth();
        }
        return tableWidth;
    }

    public float getMargin() {
        return margin;
    }

    public void setMargin(float margin) {
        this.margin = margin;
    }

    public PDRectangle getPageSize() {
        return pageSize;
    }

    public void setPageSize(PDRectangle pageSize) {
        this.pageSize = pageSize;
    }

    public PDFont getColNameTextFont() {
        return colNameTextFont;
    }

    public void setColNameTextFont(PDFont colNameTextFont) {
        this.colNameTextFont = colNameTextFont;
    }
    
    public PDFont getTextFont() {
        return textFont;
    }

    public void setTextFont(PDFont textFont) {
        this.textFont = textFont;
    }

    public float getColNameFontSize() {
        return colNameFontSize;
    }

    public void setColNameFontSize(float colNameFontSize) {
        this.colNameFontSize = colNameFontSize;
    }
    
    public float getFontSize() {
        return fontSize;
    }

    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }

    public String[] getColumnsNamesAsArray() {
        String[] columnNames = new String[getNumberOfColumns()];
        for (int i = 0; i <= getNumberOfColumns() -1; i++) {
            columnNames[i] = columns.get(i).getName();
            ////log.debug("PDFTable - getColumnsNamesAsArray="+i+"="+columns.get(i).getName());
        }
        return columnNames;
    }

    public String[] getColumnsNamesAsArrayDct() {
        String[] columnNamesDct = new String[getNumberOfColumnsDct()];
        for (int i = 0; i <= getNumberOfColumnsDct() -1; i++) {
            columnNamesDct[i] = columnsDct.get(i).getName();
            ////log.debug("PDFTable - getColumnsNamesAsArrayDct="+i+"="+columnsDct.get(i).getName());
        }
        return columnNamesDct;
    }
    
    public String[] getColumnsNamesAsArrayStuAss() {
        String[] columnNamesStuAss = new String[getNumberOfColumnsStuAss()];
        for (int i = 0; i <= getNumberOfColumnsStuAss() -1; i++) {
            columnNamesStuAss[i] = columnsStuAss.get(i).getName();
            ////log.debug("PDFTable - getColumnsNamesAsArrayStuAss="+i+"="+columnsStuAss.get(i).getName());
        }
        return columnNamesStuAss;
    }
    public List<PDFColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<PDFColumn> columns) {
        this.columns = columns;
    }

    public List<PDFColumn> getColumnsDct() {
		return columnsDct;
	}

	public void setColumnsDct(List<PDFColumn> columnsDct) {
		this.columnsDct = columnsDct;
	}

	public List<PDFColumn> getColumnsStuAss() {
		return columnsStuAss;
	}

	public void setColumnsStuAss(List<PDFColumn> columnsStuAss) {
		this.columnsStuAss = columnsStuAss;
	}

	public Integer getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(Integer numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public Integer getNumberOfRowsDct() {
		return numberOfRowsDct;
	}

	public void setNumberOfRowsDct(Integer numberOfRowsDct) {
		this.numberOfRowsDct = numberOfRowsDct;
	}

	public Integer getNumberOfRowsStuAss() {
		return numberOfRowsStuAss;
	}

	public void setNumberOfRowsStuAss(Integer numberOfRowsStuAss) {
		this.numberOfRowsStuAss = numberOfRowsStuAss;
	}

	public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getColNameRowHeight() {
        return colNameRowHeight;
    }

    public void setColNameRowHeight(float colNameRowHeight) {
        this.colNameRowHeight = colNameRowHeight;
    }
    
    public float getRowHeight() {
        return rowHeight;
    }

    public void setRowHeight(float rowHeight) {
        this.rowHeight = rowHeight;
    }

    public String[][] getContent() {
        return content;
    }

    public void setContent(String[][] content) {
        this.content = content;
    }

    public float getCellMargin() {
        return cellMargin;
    }

    public void setCellMargin(float cellMargin) {
        this.cellMargin = cellMargin;
    }

    public boolean isLandscape() {
        return isLandscape;
    }

    public void setLandscape(boolean isLandscape) {
        this.isLandscape = isLandscape;
    }
}
