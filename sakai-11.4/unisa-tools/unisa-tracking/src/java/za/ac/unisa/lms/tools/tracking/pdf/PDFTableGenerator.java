package za.ac.unisa.lms.tools.tracking.pdf;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;
import org.apache.struts.action.ActionForm;

import za.ac.unisa.lms.tools.tracking.forms.DocketNumberDetails;
import za.ac.unisa.lms.tools.tracking.forms.TrackingForm;

public class PDFTableGenerator {

    // Generates document from Table object
    @SuppressWarnings("unchecked")
	public void generatePDF(ActionForm form, String pdffullPath) throws IOException {
        PDDocument doc = null;
        try {
        	
        	TrackingForm pTrackingForm = (TrackingForm)form;
        	
        	//Reset Current Page
        	pTrackingForm.setCurrentPage(0);
        	
        	// create page.
        	doc = new PDDocument();
        	    	
        	// Page configuration
            final PDRectangle PAGE_SIZE = PDRectangle.A4;
            final float MARGIN = 50;
            final boolean IS_LANDSCAPE = false;

            // Font configuration
            final PDFont NAME_TEXT_FONT = PDType1Font.HELVETICA_BOLD;
            final float NAME_FONT_SIZE = 15;
            final PDFont TEXT_FONT = PDType1Font.HELVETICA;
            final float FONT_SIZE = 12;

            // Table configuration
            final float NAME_ROW_HEIGHT = 20;
            final float ROW_HEIGHT = 18;
            final float CELL_MARGIN = 5;
            float tableHeight = IS_LANDSCAPE ? PAGE_SIZE.getWidth() - (2 * MARGIN) : PAGE_SIZE.getHeight() - (2 * MARGIN);
        	
            //Set Cell Margin for Tables
            pTrackingForm.setReportPDFCellMargin(CELL_MARGIN);
            
            //Helvetica at 75% / 0.75em / 12px / 9pt
            pTrackingForm.setReportPDFMargin(MARGIN);
            pTrackingForm.setReportPDFColumnSize1(150);
            pTrackingForm.setReportPDFColumnSize2(150);
            pTrackingForm.setReportPDFColumnSize3(150);
            pTrackingForm.setReportPDFColumnSize4(60);
            
    		pTrackingForm.setReportPDFHeaderSize(5); //Set Initial size of the Header size for the Receipt Number, Date and User Name. These are static rows in size.
            
        	//log.info("PDFTableGenerator - reportPDF");
        	
    		//PDF Cover Dockets
    		//log.info("PDFTableGenerator - "+pTrackingForm.getNovelUserId()+" - reportPDF - Getting Dockets="+pTrackingForm.getDisplayDocketsForConsignment().size());

			int countDctInit = 0;
			int countStuAssInit = 0;
			int countTotalInit=0;
			
			//Count Dockets to initialize up nested array
			//log.info("PDFTableGenerator - "+pTrackingForm.getNovelUserId()+" - reportPDF - DisplayDocketsForConsignment.size="+pTrackingForm.getDisplayDocketsForConsignment().size());
    		if (pTrackingForm.getDisplayDocketsForConsignment().size() >0){
    			//log.info("PDFTableGenerator - "+pTrackingForm.getNovelUserId()+" - reportPDF - Initialize countDctInit");
    			
    			//Iterate to initialize the String array
    			Iterator<DocketNumberDetails> dctInit = pTrackingForm.getDisplayDocketsForConsignment().iterator();

    			if (dctInit.hasNext()){
    				//log.info("PDFTableGenerator - "+pTrackingForm.getNovelUserId()+" - reportPDF - DocketNumber Array Init Size="+pTrackingForm.getDisplayDocketsForConsignment().size());
    			     while (dctInit.hasNext()){
    			    	 DocketNumberDetails dctDetail = (DocketNumberDetails) dctInit.next();
    			    	 String DctCheck = dctDetail.getDocketNumber();
    			    	 countDctInit++;
    			     }
    			}
    			//log.info("PDFTableGenerator - "+pTrackingForm.getNovelUserId()+" - reportPDF - contentDct="+countDctInit);
    			countTotalInit = countDctInit;
    		}
    		
    		//Count Single Unique Assignments to initialize up nested array
    		//log.info("PDFTableGenerator - "+pTrackingForm.getNovelUserId()+" - reportPDF - DisplayUniqueNumbersForConsignment.size="+pTrackingForm.getDisplayUniqueNumbersForConsignment().size());
    		if (pTrackingForm.getDisplayUniqueNumbersForConsignment().size() >0){
    			//log.info("PDFTableGenerator - "+pTrackingForm.getNovelUserId()+" - reportPDF - Initialize countStuAssInit");
    			
    			//Iterate to initialize the String array
    			Iterator<DocketNumberDetails> stuAssInit = pTrackingForm.getDisplayUniqueNumbersForConsignment().iterator();
    			if (stuAssInit.hasNext()){
    			     while (stuAssInit.hasNext()){
    			    	 DocketNumberDetails stuAssDetail = (DocketNumberDetails) stuAssInit.next();
    			    	 String stuAssCheck = stuAssDetail.getStudentNumber();
    			    	 countStuAssInit++;
    			     }
    			}
    			//log.info("PDFTableGenerator - "+pTrackingForm.getNovelUserId()+" - reportPDF - countStuAssInit="+countStuAssInit);

    		}
			countTotalInit = countTotalInit + countStuAssInit + 1; //Add 1 for the Unique Assignment Header
    		//log.info("PDFTableGenerator - "+pTrackingForm.getNovelUserId()+" - reportPDF - countTotalInit="+countTotalInit);

   			String[][] tableContent = new String[countTotalInit][4];
   			int countDct=0;
   			int countStuAss=0;
   			int countStuAssOnly = 0;
   			
   			if (pTrackingForm.getDisplayDocketsForConsignment().size() >0){
   				
   				Iterator<DocketNumberDetails> it = pTrackingForm.getDisplayDocketsForConsignment().iterator();
   				if (it.hasNext()){
    				//log.info("PDFTableGenerator - "+pTrackingForm.getNovelUserId()+" - reportPDF - DocketNumberDetails");
    			    String tempDocketNumber = "";
    			    while (it.hasNext()){
    			    	 DocketNumberDetails detail = (DocketNumberDetails) it.next();
    			    	 if (!tempDocketNumber.equalsIgnoreCase(detail.getDocketNumber())){
    			    		 tableContent[countDct][0] = detail.getDocketNumber();
    			    		 tableContent[countDct][1] = detail.getStudentNumber();
    			    		 tableContent[countDct][2] = detail.getUniqueAssignmentNumber();
    			    		 tableContent[countDct][3] = " ";
     			    	 }else{
     			    		tableContent[countDct][0] = " ";
     			    		tableContent[countDct][1] = detail.getStudentNumber();
     			    		tableContent[countDct][2] = detail.getUniqueAssignmentNumber();
     			    		tableContent[countDct][3] = " ";
    			    	 }
    			    	 countDct++;
    			    }
   				}
   			/*}else{
   				tableContent[countDct][0] = "N/A";
   				tableContent[countDct][1] = " ";
   				tableContent[countDct][2] = " ";
   				tableContent[countDct][3] = " ";
   				countDct++;
   			*/
   			}
    			
   			pTrackingForm.setCountPDFDct(countDct);
   			//log.info("PDFTableGenerator - "+pTrackingForm.getNovelUserId()+" - reportPDF - Final Docket Count="+countDct);
   			
   			
   			// Get Single Assignments and create table
   			if (pTrackingForm.getDisplayUniqueNumbersForConsignment().size() >0){
   	  			//log.info("PDFTableGenerator - "+pTrackingForm.getNovelUserId()+" - reportPDF - Get Unique Assignments");

   				countStuAss = countDct; //Need to continue array from Cover Dockets. If Dockets=0, tableContent will automatically start from 0;

   				//log.info("PDFTableGenerator - "+pTrackingForm.getNovelUserId()+" - reportPDF - countStuAss="+countStuAss);
   				
   	   			tableContent[countStuAss][0] = "Single Assignments";  
   	   			tableContent[countStuAss][1] = "Student Number";
   	   			tableContent[countStuAss][2] = "Unique Assignment";
   	   			tableContent[countStuAss][3] = "Present";
   		   		
   	   			//log.info("PDFTableGenerator - "+pTrackingForm.getNovelUserId()+" - reportPDF - Unique Assignment Heading added to list. countStuAss="+countStuAss );
   	   			
    			Iterator<DocketNumberDetails> it = pTrackingForm.getDisplayUniqueNumbersForConsignment().iterator();
    			
    			if (it.hasNext()){
       	   			countStuAss++ ; //Need to continue array from Header. If Header=0, it will automatically start from 1;
    	   			//log.info("PDFTableGenerator - "+pTrackingForm.getNovelUserId()+" - reportPDF - Add another 1 to Docket Count - countStuAss="+countStuAss);
    			    while (it.hasNext()){
    			    	 DocketNumberDetails detail = (DocketNumberDetails) it.next();
    			    	 //log.info("PDFTableGenerator - "+pTrackingForm.getNovelUserId()+" - reportPDF - Student="+detail.getStudentNumber()+", Unique Assignments="+detail.getUniqueAssignmentNumber());

    			    	 tableContent[countStuAss][0] = "";
	    			     tableContent[countStuAss][1] = detail.getStudentNumber();
	    			     tableContent[countStuAss][2] = detail.getUniqueAssignmentNumber();
	    			     tableContent[countStuAss][3] = " ";
	    			     countStuAss++;
	    			     countStuAssOnly++;
    			    	 //log.info("PDFTableGenerator - "+pTrackingForm.getNovelUserId()+" - reportPDF - End While Loop - countStuAss="+countStuAss+", countStuAssOnly="+countStuAssOnly);
    			     }
			    	 //log.info("PDFTableGenerator - "+pTrackingForm.getNovelUserId()+" - reportPDF - Out of While Loop - countStuAss="+countStuAss+", countStuAssOnly="+countStuAssOnly);
    			}
		    	 //log.info("PDFTableGenerator - "+pTrackingForm.getNovelUserId()+" - reportPDF - End of If");
    		/*}else{
    			//log.info("PDFTableGenerator - "+pTrackingForm.getNovelUserId()+" - No Unique Assignments to display");
    			tableContent[countStuAss][0] = "N/A";
    			tableContent[countStuAss][1] = " ";
    			tableContent[countStuAss][2] = " ";
    			tableContent[countStuAss][3] = " ";
    			countStuAssOnly = 1;
    		*/
    		}
	    	 
   			if (countDct != 0) {
   				countStuAssOnly = countDct;
   			}
   			//log.info("PDFTableGenerator - "+pTrackingForm.getNovelUserId()+" - reportPDF - setCountPDFStuAss="+countStuAssOnly);
    		pTrackingForm.setCountPDFStuAss(countStuAssOnly);
    		
    		//log.info("PDFTableGenerator - "+pTrackingForm.getNovelUserId()+" - reportPDF - Final StuAss Count="+countStuAssOnly);
    		//log.info("PDFTableGenerator - "+pTrackingForm.getNovelUserId()+" - reportPDF - tableContent.length="+tableContent.length);
    		
    		
    		/*
    		//log.info("Checking Cover Dockets & Assignments  : Start");
    		for (int i=0; i < tableContent.length;i++){
		       	//log.info("Cover Docket     : ["+i+"]" + tableContent[i][0]+"\n\r");
		       	//log.info("Student Number   : ["+i+"]" + tableContent[i][1]+"\n\r");
		       	//log.info("Unique Assignment: ["+i+"]" + tableContent[i][2]+"\n\r");
		       	//log.info("Present          : ["+i+"]" + tableContent[i][3]+"\n\r");

		    }
    		//log.info("Checking Cover Dockets & Assignments  : End");
   			*/
    		
    		// Total size of columns must not be greater than table width.
	   	    List<PDFColumn> columnsDct = new ArrayList<PDFColumn>();
	   	    columnsDct.add(new PDFColumn("Cover Dockets", pTrackingForm.getReportPDFColumnSize1()));  
	   	    columnsDct.add(new PDFColumn("Student Number", pTrackingForm.getReportPDFColumnSize2()));
	   	    columnsDct.add(new PDFColumn("Unique Assignment", pTrackingForm.getReportPDFColumnSize3()));
	   	    columnsDct.add(new PDFColumn("Present", pTrackingForm.getReportPDFColumnSize4()));
	   	        
	   		// Total size of columns must not be greater than table width.
	   		List<PDFColumn> columnsStuAss = new ArrayList<PDFColumn>();
	   		columnsStuAss.add(new PDFColumn("Single Assignments", pTrackingForm.getReportPDFColumnSize1()));  
	   		columnsStuAss.add(new PDFColumn("Student Number", pTrackingForm.getReportPDFColumnSize2()));
	   		columnsStuAss.add(new PDFColumn("Unique Assignment", pTrackingForm.getReportPDFColumnSize3()));
	   		columnsStuAss.add(new PDFColumn("Present", pTrackingForm.getReportPDFColumnSize4()));
	   		   
	   	    //log.info("PDFTableGenerator - "+pTrackingForm.getNovelUserId()+" - reportPDF - Setting Up Docket Table");
		    PDFTable table = new PDFTableBuilder()
		    	.setCellMargin(CELL_MARGIN)
		    	.setColumnsDct(columnsDct)
		    	.setColumnsStuAss(columnsStuAss)
		    	.setContent(tableContent)
		    	.setHeight(tableHeight)
		    	.setNumberOfRows(tableContent.length)
		    	.setNumberOfRowsDct(pTrackingForm.getCountPDFDct())
		    	.setNumberOfRowsStuAss(pTrackingForm.getCountPDFStuAss())
		    	.setRowHeight(ROW_HEIGHT)
		    	.setColNameRowHeight(NAME_ROW_HEIGHT)
		    	.setMargin(MARGIN)
		    	.setPageSize(PAGE_SIZE)
		    	.setLandscape(IS_LANDSCAPE)
		    	.setColNameTextFont(NAME_TEXT_FONT)
		    	.setColNameFontSize(14)
		    	.setTextFont(TEXT_FONT)
		    	.setFontSize(FONT_SIZE)
		    	.build();
		    //return table;
	    		    
		    // Create Tables
		    //log.info("PDFTableGenerator - generatePDF - End PDF Table");
		    drawTable(doc, table, form);
		    
		    //Save the File
		    DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
		    Date date = new Date();
		    String fileName = "UNISA_HardCopy_Tracking_"+pTrackingForm.getDisplayShipListNumber()+"_"+pTrackingForm.getNovelUserId()+"_"+pTrackingForm.getUserSelection().toString().toUpperCase()+"_"+dateFormat.format(date);
		    pTrackingForm.setReportPDFToDownload(fileName);
		    doc.save( pdffullPath+"/"+fileName+".pdf");
		    //log.info("PDFTableGenerator - generatePDF - Doc Save="+pdffullPath+"/"+fileName+".pdf");
        } finally {
            if (doc != null) {
                doc.close();
            }
        }
    }

    // Configures Header
    public void drawHeader(PDDocument doc, PDPageContentStream contentStream, ActionForm form) throws IOException {
    	
    	//log.info("PDFTableGenerator - drawHeader - Start");
    	TrackingForm pTrackingForm = (TrackingForm) form;
    	
        	// Initialize Top Section/Header
        	PDFont ShipListFont = PDType1Font.HELVETICA_BOLD;
        	PDFont HeaderFont = PDType1Font.HELVETICA;
	    	float ShipListFontSize = 18;
	    	float HeaderFontSize = 14;
            int leftMargin = (int) pTrackingForm.getReportPDFMargin();
            int startY = 800;
            int nextY = 0;
            int leadingSpace = 16;
            
        	// Header Text
            contentStream.setFont(ShipListFont, ShipListFontSize);
            contentStream.setLeading(leadingSpace);
            contentStream.beginText();
            
            nextY = startY;
            contentStream.newLineAtOffset(leftMargin, nextY);
            if (pTrackingForm.getUserSelection().equalsIgnoreCase("IN")){
            	contentStream.showText("RECEIPT: "+pTrackingForm.getDisplayShipListNumber());
            }else{
            	contentStream.showText("CONSIGNMENT LIST: "+pTrackingForm.getDisplayShipListNumber());
            }
            contentStream.endText();
            
            contentStream.beginText();
            nextY = nextY - leadingSpace;
            contentStream.newLineAtOffset(leftMargin, nextY);
            contentStream.setFont(HeaderFont, HeaderFontSize);
	    	contentStream.showText("Booked "+pTrackingForm.getUserSelection()+" on "+pTrackingForm.getDisplayShipListDate());
	    	contentStream.endText();
            
            contentStream.beginText();
	    	nextY = nextY - leadingSpace;
	    	contentStream.newLineAtOffset(leftMargin, nextY);
	    	contentStream.showText("By User: "+pTrackingForm.getNovelUserId());
	    	contentStream.endText();
            
            contentStream.beginText();
	    	nextY = nextY - leadingSpace;
	    	contentStream.newLineAtOffset(leftMargin, nextY);
	    	contentStream.showText(" "); //Insert Empty line after header for spacing.
	    	contentStream.endText();
	    	
	    	//PDF Address:
	    	boolean textBoxFill = false;
            Color textBoxColor = Color.GRAY;
            
            if (pTrackingForm.getDisplayAddress1() != null && !pTrackingForm.getDisplayAddress1().equals("") && !pTrackingForm.getDisplayAddress1().equals(" ")){
				//log.info("PDFTableGenerator - drawHeader - Start Address");
				//Set Address Header
				contentStream.beginText();
				nextY = nextY - leadingSpace;
				//log.info("nextY Header Address="+nextY);
				contentStream.newLineAtOffset(leftMargin+pTrackingForm.getReportPDFCellMargin(), nextY);
				String AddressHead = "Destination Address";
				contentStream.showText(AddressHead);
				contentStream.endText();
				
	            // Draw Block around Destination Address
	            int textBoxStartX = (int) (leftMargin+pTrackingForm.getReportPDFCellMargin()-2);
	            int textBoxStartY = (int) (nextY-2);
		        int textBoxWidth = (pTrackingForm.getReportPDFColumnSize1()+pTrackingForm.getReportPDFColumnSize2()+pTrackingForm.getReportPDFColumnSize3()+pTrackingForm.getReportPDFColumnSize4());
		        int textBoxHeight = (int) (HeaderFontSize+4);
	    		
	            // Draw Column rectangle
	            //log.info("Draw Address Header Block, TextBoxStartX="+textBoxStartX+", TextBoxStartY="+textBoxStartY+", TextBoxWidth="+textBoxWidth+", TextBoxHeight="+textBoxHeight);
	            drawRect(contentStream, textBoxColor, new java.awt.Rectangle(textBoxStartX, textBoxStartY, textBoxWidth, textBoxHeight), textBoxFill);
    
	            //Set Address Line 1
	            contentStream.beginText();
				nextY = nextY - leadingSpace;
				//log.info("nextY A1="+nextY);
		    	contentStream.newLineAtOffset(leftMargin+pTrackingForm.getReportPDFCellMargin(), nextY);
		    	contentStream.showText(pTrackingForm.getDisplayAddress1());
		    	contentStream.endText();
				
				// Draw Block around Address line 1
				textBoxStartY = nextY-4;
				textBoxHeight = (int) (HeaderFontSize+4);
				// Draw Column rectangle
	            //log.info("Draw Address Header Block, TextBoxStartX="+textBoxStartX+", TextBoxStartY="+textBoxStartY+", TextBoxWidth="+textBoxWidth+", TextBoxHeight="+textBoxHeight);
	            //drawRect(contentStream, textBoxColor, new java.awt.Rectangle(textBoxStartX, textBoxStartY, textBoxWidth, textBoxHeight), textBoxFill);
		
				if (pTrackingForm.getDisplayAddress2() != null && !pTrackingForm.getDisplayAddress2().equals("") && !pTrackingForm.getDisplayAddress2().equals(" ")){
					// Set Address Line 2
					contentStream.beginText();
					nextY = nextY - leadingSpace;
					//log.info("nextY A2="+nextY);
			    	contentStream.newLineAtOffset(leftMargin+pTrackingForm.getReportPDFCellMargin(), nextY);
					contentStream.showText(pTrackingForm.getDisplayAddress2());
			    	contentStream.endText();
					
					// Draw Block around Address line 1
					textBoxStartY = nextY-4;
					textBoxHeight = textBoxHeight + (int) (HeaderFontSize+2);
					// Draw Column rectangle
		            //log.info("Draw Address Header Block, TextBoxStartX="+textBoxStartX+", TextBoxStartY="+textBoxStartY+", TextBoxWidth="+textBoxWidth+", TextBoxHeight="+textBoxHeight);
		            //drawRect(contentStream, textBoxColor, new java.awt.Rectangle(textBoxStartX, textBoxStartY, textBoxWidth, textBoxHeight), textBoxFill);

				}
				if (pTrackingForm.getDisplayAddress3() != null && !pTrackingForm.getDisplayAddress3().equals("") && !pTrackingForm.getDisplayAddress3().equals(" ")){
					// Set Address Line 3
					contentStream.beginText();
					nextY = nextY - leadingSpace;
					//log.info("nextY A3="+nextY);
			    	contentStream.newLineAtOffset(leftMargin+pTrackingForm.getReportPDFCellMargin(), nextY);
					contentStream.showText(pTrackingForm.getDisplayAddress3());
			    	contentStream.endText();
					
					// Draw Block around Address line 1
					textBoxStartY = nextY-4;
					textBoxHeight = textBoxHeight + (int) (HeaderFontSize+2);
					// Draw Column rectangle
		            //log.info("Draw Address Header Block, TextBoxStartX="+textBoxStartX+", TextBoxStartY="+textBoxStartY+", TextBoxWidth="+textBoxWidth+", TextBoxHeight="+textBoxHeight);
		            //drawRect(contentStream, textBoxColor, new java.awt.Rectangle(textBoxStartX, textBoxStartY, textBoxWidth, textBoxHeight), textBoxFill);

				}
				if (pTrackingForm.getDisplayAddress4() != null && !pTrackingForm.getDisplayAddress4().equals("") && !pTrackingForm.getDisplayAddress4().equals(" ")){
					// Set Address Line 4
					contentStream.beginText();
					nextY = nextY - leadingSpace;
					//log.info("nextY A4="+nextY);
			    	contentStream.newLineAtOffset(leftMargin+pTrackingForm.getReportPDFCellMargin(), nextY);
					contentStream.showText(pTrackingForm.getDisplayAddress4());
			    	contentStream.endText();
					
					// Draw Block around Address line 1
					textBoxStartY = nextY-4;
					textBoxHeight = textBoxHeight + (int) (HeaderFontSize+2);
					// Draw Column rectangle
		            //log.info("Draw Address Header Block, TextBoxStartX="+textBoxStartX+", TextBoxStartY="+textBoxStartY+", TextBoxWidth="+textBoxWidth+", TextBoxHeight="+textBoxHeight);
		            //drawRect(contentStream, textBoxColor, new java.awt.Rectangle(textBoxStartX, textBoxStartY, textBoxWidth, textBoxHeight), textBoxFill);

				}
				if (pTrackingForm.getDisplayPostal() != null && !pTrackingForm.getDisplayPostal().equals("") && !pTrackingForm.getDisplayPostal().equals(" ") && !pTrackingForm.getDisplayPostal().equals("0")){
					// Set Address Postal
					contentStream.beginText();
					nextY = nextY - leadingSpace;
					//log.info("nextY A5="+nextY);
			    	contentStream.newLineAtOffset(leftMargin+pTrackingForm.getReportPDFCellMargin(), nextY);
					contentStream.showText(pTrackingForm.getDisplayPostal());
					contentStream.endText();
					
					// Draw Block around Address line 1
					textBoxStartY = nextY-4;
					textBoxHeight = textBoxHeight + (int) (HeaderFontSize+2);
					// Draw Column rectangle
		            //log.info("Draw Address Header Block, TextBoxStartX="+textBoxStartX+", TextBoxStartY="+textBoxStartY+", TextBoxWidth="+textBoxWidth+", TextBoxHeight="+textBoxHeight);
		            //drawRect(contentStream, textBoxColor, new java.awt.Rectangle(textBoxStartX, textBoxStartY, textBoxWidth, textBoxHeight), textBoxFill);

				}
				if (pTrackingForm.getDisplayEmail() != null && !pTrackingForm.getDisplayEmail().equals("") && !pTrackingForm.getDisplayEmail().equals(" ")){
					// Set Address Email
					contentStream.beginText();
					nextY = nextY - leadingSpace;
					//log.info("nextY A6="+nextY);
			    	contentStream.newLineAtOffset(leftMargin+pTrackingForm.getReportPDFCellMargin(), nextY);
					contentStream.showText(pTrackingForm.getDisplayEmail());
					contentStream.endText();
					
					// Draw Block around Address line 1
					textBoxStartY = nextY-4;
					textBoxHeight = textBoxHeight + (int) (HeaderFontSize+2);
					// Draw Column rectangle
		            //log.info("Draw Address Header Block, TextBoxStartX="+textBoxStartX+", TextBoxStartY="+textBoxStartY+", TextBoxWidth="+textBoxWidth+", TextBoxHeight="+textBoxHeight);
		            //drawRect(contentStream, textBoxColor, new java.awt.Rectangle(textBoxStartX, textBoxStartY, textBoxWidth, textBoxHeight), textBoxFill);

				}
		            drawRect(contentStream, textBoxColor, new java.awt.Rectangle(textBoxStartX, textBoxStartY, textBoxWidth, textBoxHeight), textBoxFill);
			}  //End Address
			//log.info("PDFTableGenerator - drawHeader - End Address");
			
            // Convert Unisa image code to bytes.
			//log.info("PDFTableGenerator - drawHeader - Add Image");
            byte[] decodedBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(TrackingForm.getUnisaImage());

            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(decodedBytes));
            PDImageXObject  pdImageXObject = LosslessFactory.createFromImage(doc, bufferedImage);
            
            if (bufferedImage == null) {
              	 //log.info("PDFTableGenerator - drawHeader - Buffered Image is null");
            }else{

	            //Image for Header
	            float imageScale = 0.3f;
	            float imageStartX = 360;
	            float imageStartY = 780;
	            float imageWidth = pdImageXObject.getWidth()*imageScale;
	            float imageHeight = pdImageXObject.getHeight()*imageScale;
	            
	            contentStream.drawImage(pdImageXObject, imageStartX, imageStartY, imageWidth, imageHeight);
	            //contentStream.drawImage(image, 425, 675 );
            }
    }
    
    // Configures Footer
    public void drawFooter(PDDocument doc, PDPageContentStream contentStream, int pageCount, int totalPages, ActionForm form) throws IOException {
    	
    	//log.info("PDFTableGenerator - drawFooter - Start - pageCount="+pageCount+", totalPages="+totalPages);
    	
    		TrackingForm pTrackingForm = (TrackingForm) form;
    	
        	// Initialize Top Section/Header
        	PDFont HeaderFont = PDType1Font.HELVETICA;
	    	float HeaderFontSize = 12;
            int leftMargin = (int) pTrackingForm.getReportPDFMargin();
            int endY = 30;
            int leadingSpace = 16;
            
            //Set Page number one more than index
            pageCount = pageCount +1;
            
        	// Header Text
            contentStream.setFont(HeaderFont, HeaderFontSize);
            contentStream.setLeading(leadingSpace);
            contentStream.beginText();
			contentStream.newLineAtOffset(leftMargin,endY);
			contentStream.showText("Signature __________________  Date: ___/___/20___                               Page "+pageCount+" of "+totalPages);
			contentStream.endText();
			contentStream.close();
	}

    // Configures basic setup for the table and draws it page by page
    public void drawTable(PDDocument doc, PDFTable table, ActionForm form) throws IOException {
    	
    	TrackingForm pTrackingForm = (TrackingForm) form;
    	
        // Calculate pagination
    	//See if Booking In or Out. If Out, get number of address rows required at top.
    	//if ("OUT".equalsIgnoreCase(pTrackingForm.getUserSelection())){
    	if ("xxx".equalsIgnoreCase("xxx")){ //hardcoded to 6 lines to keep standard form layout
    		//Add 1 to Header Size for "Destination Address Header"
            pTrackingForm.setReportPDFHeaderSize(pTrackingForm.getReportPDFHeaderSize()+1); //Set Header Count to determine final number of rows for tables
            //Add 1 to Header size for Address Line 1
            pTrackingForm.setReportPDFHeaderSize(pTrackingForm.getReportPDFHeaderSize()+1); //Set Header Count to determine final number of rows for tables
			
			//xxx if (pTrackingForm.getDisplayAddress2() != null && !pTrackingForm.getDisplayAddress2().equals("") && !pTrackingForm.getDisplayAddress2().equals(" ")){
	            pTrackingForm.setReportPDFHeaderSize(pTrackingForm.getReportPDFHeaderSize()+1); //Set Header Count to determine final number of rows for tables
			//}
			//xxx if (pTrackingForm.getDisplayAddress3() != null && !pTrackingForm.getDisplayAddress3().equals("") && !pTrackingForm.getDisplayAddress3().equals(" ")){
	            pTrackingForm.setReportPDFHeaderSize(pTrackingForm.getReportPDFHeaderSize()+1); //Set Header Count to determine final number of rows for tables
			//}
			//xxx if (pTrackingForm.getDisplayAddress4() != null && !pTrackingForm.getDisplayAddress4().equals("") && !pTrackingForm.getDisplayAddress4().equals(" ")){
	            pTrackingForm.setReportPDFHeaderSize(pTrackingForm.getReportPDFHeaderSize()+1); //Set Header Count to determine final number of rows for tables
			//}
			//xxx if (pTrackingForm.getDisplayPostal() != null && !pTrackingForm.getDisplayPostal().equals("") && !pTrackingForm.getDisplayPostal().equals(" ") && !pTrackingForm.getDisplayPostal().equals("0")){
	            pTrackingForm.setReportPDFHeaderSize(pTrackingForm.getReportPDFHeaderSize()+1); //Set Header Count to determine final number of rows for tables
			//}
			//xxx if (pTrackingForm.getDisplayEmail() != null && !pTrackingForm.getDisplayEmail().equals("") && !pTrackingForm.getDisplayEmail().equals(" ")){
	            pTrackingForm.setReportPDFHeaderSize(pTrackingForm.getReportPDFHeaderSize()+1); //Set Header Count to determine final number of rows for tables
			//}
		}
        Integer rowsPerPage = new Double(Math.floor(table.getHeight() / table.getRowHeight())).intValue() - pTrackingForm.getReportPDFHeaderSize(); // subtract header and address
        Integer numberOfPages = new Double(Math.ceil(table.getNumberOfRows().floatValue() / rowsPerPage)).intValue();
        
        //log.info("PDFTableGenerator - drawTable - ReportPDFHeaderSize="+pTrackingForm.getReportPDFHeaderSize());
        //log.info("PDFTableGenerator - drawTable - Final RowsPerPage="+rowsPerPage);
        //log.info("PDFTableGenerator - drawTable - Total NumberOfPages="+numberOfPages);

        // Generate each page, get the content and draw it
        for (int pageCount = 0; pageCount < numberOfPages; pageCount++) {
            PDPage page = generatePage(doc, table);
            PDPageContentStream contentStream = generateContentStream(doc, page, table);
            
            drawHeader(doc, contentStream, form);
        	// Initialize Top Section/Header
        	PDFont ShipListFont = PDType1Font.HELVETICA_BOLD;
        	PDFont HeaderFont = PDType1Font.HELVETICA;
	    	float ShipListFontSize = 18;
	    	float HeaderFontSize = 14;
            
        	// Header Text
	    	//PDPage page = new PDPage(PDRectangle.A4);
            //contentStream = new PDPageContentStream(doc,page);
            contentStream.setFont(table.getTextFont(), table.getFontSize());
            String[][] currentPageContent = getContentForCurrentPage(table, rowsPerPage, pageCount);
            int currentPage = pageCount+1;
            //log.info("PDFTableGenerator - drawTable - drawTable - currentPage="+currentPage);
            drawCurrentPage(table, currentPageContent, contentStream, form, currentPage);
            drawFooter(doc, contentStream, pageCount, numberOfPages, form);
            pTrackingForm.setCurrentPage(currentPage); //set currentPage to this page, so new one will not be the same and will therefore write the Column header again.
        }
    }

    // Draws current page table grid and border lines and content
    private void drawCurrentPage(PDFTable table, String[][] currentPageContent, PDPageContentStream contentStream, ActionForm form, int pageCount) throws IOException {
    	
    	TrackingForm pTrackingForm = (TrackingForm) form;
    	
    	//log.info("PDFTableGenerator - drawCurrentPage - PDF Processing table");
    	
        float tableTopY = 0;
        
        //log.info("PDFTableGenerator - drawCurrentPage - PDF Processing tableTopY="+tableTopY);
        
       	tableTopY = table.isLandscape() ? table.getPageSize().getWidth() - table.getMargin() : table.getPageSize().getHeight() - table.getMargin();
        tableTopY = tableTopY - (pTrackingForm.getReportPDFHeaderSize()*(table.getFontSize()+2)); //Subtract Header and any Addresses if Booking Out
        //log.info("PDFTableGenerator - drawCurrentPage - PDF Processing TableStart="+pTrackingForm.getTableStartY()+", tableTopY="+tableTopY+", Header: "+pTrackingForm.getReportPDFHeaderSize()+" * "+table.getFontSize()+" = "+pTrackingForm.getReportPDFHeaderSize()*table.getFontSize());
        // Draws grid and borders
        //drawTableGrid(table, currentPageContent, contentStream, tableTopY);

        // Position cursor to start drawing content
        float nextTextX = table.getMargin() + table.getCellMargin();
        // Calculate center alignment for text in cell considering font height
        float nextTextY = tableTopY - (table.getColNameRowHeight() / 2)
                - ((table.getColNameTextFont().getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * table.getColNameFontSize()) / 4);
        //log.info("PDFTableGenerator - drawCurrentPage - tableTopY="+tableTopY);
        //log.info("PDFTableGenerator - drawCurrentPage - ColNameRowHeight="+table.getColNameRowHeight());
        //log.info("PDFTableGenerator - drawCurrentPage - FontBoundingBox="+table.getColNameTextFont().getFontDescriptor().getFontBoundingBox().getHeight());
        //log.info("PDFTableGenerator - drawCurrentPage - ColNameFontSize()="+table.getColNameFontSize());
        //log.info("PDFTableGenerator - drawCurrentPage - First nextTextY="+nextTextY);
        
        nextTextY = nextTextY + 2;
        //log.info("PDFTableGenerator - drawCurrentPage - Second nextTextY="+nextTextY);
        
        // Write content
        //log.info("PDFTableGenerator - drawCurrentPage - Write Content - Page="+pageCount+", PageContent="+currentPageContent.length);
        
        //log.info("PDFTableGenerator - drawCurrentPage - Write Content - CurrentPage="+pTrackingForm.getCurrentPage()+", PageNow="+pageCount);
       
        if (pTrackingForm.getCurrentPage() != pageCount){  //Checking first line of new page
        	
        	//log.info("PDFTableGenerator - drawCurrentPage - Write Content - in If");
        	// Write column headers
        	//log.info("PDFTableGenerator - drawCurrentPage - Before Column Headers - Page="+pTrackingForm.getCurrentPage());
        	String[] lineContent = currentPageContent[0];
        	//log.info("PDFTableGenerator - drawCurrentPage - Got currentPageContent");
        	String colContent = lineContent[0]; //Get only the first column to see if it is a header or a number in the case of a Cover Docket
        	//log.info("PDFTableGenerator - drawCurrentPage - Got lineContent");
        	//log.info("PDFTableGenerator - drawCurrentPage - colContent="+colContent);
        	if (colContent != null){
	        	boolean checkNo = false;
	        	if (colContent != null && !colContent.equals("")){
	        		checkNo = colContent.matches("[+-]?\\d*(\\.\\d+)?");
	        	}
	        	//log.info("PDFTableGenerator - drawCurrentPage - Header Check="+colContent+", isDocket="+checkNo);
	        	if (checkNo && !colContent.equalsIgnoreCase("Cover Docket")  && !colContent.equalsIgnoreCase("Single Assignments")){
	        		//log.info("PDFTableGenerator - drawCurrentPage - Docket Headers");
	        		contentStream.setFont(table.getColNameTextFont(), table.getColNameFontSize());
	                writeContentLine(table.getColumnsNamesAsArrayDct(), contentStream, nextTextX, nextTextY, table, "TableHeader", form);
	                // Calculate center alignment for text in cell considering font height
	                nextTextY -= table.getRowHeight();
	                nextTextX = table.getMargin() + table.getCellMargin();
	                //log.info("PDFTableGenerator - drawCurrentPage - After Docket Column Headers");
	                //log.info("PDFTableGenerator - drawCurrentPage - nextTextY="+nextTextY);
	                //log.info("PDFTableGenerator - drawCurrentPage - nextTextX="+nextTextX);
	        	}else if(!checkNo && !colContent.equalsIgnoreCase("Cover Docket")  && !colContent.equalsIgnoreCase("Single Assignments")) {
	        		//log.info("PDFTableGenerator - drawCurrentPage - StuAss Headers");
	        		contentStream.setFont(table.getColNameTextFont(), table.getColNameFontSize());
	                writeContentLine(table.getColumnsNamesAsArrayStuAss(), contentStream, nextTextX, nextTextY, table, "TableHeader", form);
	                // Calculate center alignment for text in cell considering font height
	                nextTextY -= table.getRowHeight();
	                nextTextX = table.getMargin() + table.getCellMargin();
	                //log.info("PDFTableGenerator - drawCurrentPage - After StuAss Column Headers");
	                //log.info("PDFTableGenerator - drawCurrentPage - nextTextY="+nextTextY);
	                //log.info("PDFTableGenerator - drawCurrentPage - nextTextX="+nextTextX);
	        	}else{
	        		//log.info("PDFTableGenerator - drawCurrentPage - After Column Headers - Else");
	        	}
        	}else{
        		//log.info("PDFTableGenerator - drawCurrentPage - Write Content - No Header Content");
        	}
    	}else{
    		//log.info("PDFTableGenerator - drawCurrentPage - Write Content - in Else - End of Checking Header");
    	}
        
        //log.info("PDFTableGenerator - drawCurrentPage - Write Content - Now Write Lines");
        for (int i = 0; i < currentPageContent.length; i++) {
        	//log.info("PDFTableGenerator - drawCurrentPage - Before Write Content - nextTextX="+nextTextX+", - nextTextY="+nextTextY);
        	contentStream.setFont(table.getTextFont(), table.getFontSize());
            writeContentLine(currentPageContent[i], contentStream, nextTextX, nextTextY, table, "TableContent", form);
            nextTextY -= table.getRowHeight();
            nextTextX = table.getMargin() + table.getCellMargin();
            //log.info("PDFTableGenerator - drawCurrentPage - After Write Content - nextTextX="+nextTextX+", - nextTextY="+nextTextY);
            
        }
         //contentStream.close();
    }

    // Writes the content for one line
    @SuppressWarnings("unused")
	private void writeContentLine(String[] lineContent, PDPageContentStream contentStream, float nextTextX, float nextTextY,
    		PDFTable table, String contentType, ActionForm form) throws IOException {
    	
    	//log.info("PDFTableGenerator - writeContentLine");
    	TrackingForm pTrackingForm = (TrackingForm) form;
    	
    	if (contentType.equalsIgnoreCase("TableHeader")){
    		contentStream.setFont(table.getColNameTextFont(), table.getColNameFontSize());
    		//log.info("PDFTableGenerator - writeContentLine - TableHeader - ColNameTextFont="+table.getColNameTextFont()+", ColNameFontSize="+table.getColNameFontSize());
    	}else{
    		contentStream.setFont(table.getTextFont(), table.getFontSize());
    		//log.info("PDFTableGenerator - writeContentLine - TableHeader - TextFont="+table.getTextFont()+", FontSize="+table.getFontSize());
    	}
    	//log.info("PDFTableGenerator - writeContentLine - NumberOfColumns="+table.getNumberOfColumnsStuAss());
    	
        for (int i = 0; i < table.getNumberOfColumnsStuAss(); i++) {
           
        	//log.info("PDFTableGenerator - writeContentLine - In For Loop");

        	String text = lineContent[i];
            
        	if (text == null){
        		text = " ";
        	}
        	
        	//log.info("PDFTableGenerator - writeContentLine - LineContent Text="+text);

            boolean textBoxFill = false;
            Color textBoxColor = Color.GRAY;
            float textStartX = 0; //Integer to work out center or end of column
            float textMiddle = 0;
            int textLength = 0;
            int textBoxWidth = 0;
            int textBoxHeight = (int) (table.getColNameFontSize()+4);
    		
            //Get Text Length
    		textLength=text.length()*8; //Multiply by 9 as Helvetica is 75% of Arial and 9pt per Character.
    		
    		if (i==0){
            	textBoxWidth = pTrackingForm.getReportPDFColumnSize1();
            }else if (i==1){
            	textBoxWidth = pTrackingForm.getReportPDFColumnSize2();
            }else if (i==2){
            	textBoxWidth = pTrackingForm.getReportPDFColumnSize3();
            }else if (i==3){
            	textBoxWidth = pTrackingForm.getReportPDFColumnSize4();
            }
    		
    		//Find Center of Column
    		textMiddle = textBoxWidth/2;
    		//log.info("PDFTableGenerator - writeContentLine - textMiddle="+textMiddle);
    		
            boolean checkNo = text.matches("[+-]?\\d*(\\.\\d+)?");
        	if (!checkNo){
        		//log.info("PDFTableGenerator - writeContentLine - Text="+text+", checkNo="+checkNo+" - Writng Header");
        		contentStream.setFont(table.getColNameTextFont(), table.getColNameFontSize());
        		textStartX = nextTextX + (textMiddle - (textLength/2));
        	}else{
        		//log.info("PDFTableGenerator - writeContentLine - Text="+text+", checkNo="+checkNo+" - Writng Content");
        		contentStream.setFont(table.getTextFont(), table.getFontSize());
        		textStartX = nextTextX + (textBoxWidth - textLength - 2);
        		//Helvetica at 75% / 0.75em / 12px / 9pt
        	}
            
    		//log.info("PDFTableGenerator - writeContentLine - nextTextX="+nextTextX+", TextLength="+textLength+", TextBoxWidth="+textBoxWidth+", TextMiddle="+textMiddle+", textStartX="+textStartX);

            contentStream.beginText();
            //contentStream.newLineAtOffset(nextTextX, nextTextY);
            contentStream.newLineAtOffset(textStartX, nextTextY);
            contentStream.showText(text);
            contentStream.endText();
            
            // Draw Block around line
            float initMove = nextTextX;
            float initEnd = nextTextY;
            
            //log.info("Draw Block initMove(nextTextX)="+nextTextX);
            //log.info("Draw Block initEnd(nextTextY)="+nextTextY);
	            
            int textBoxStartX = (int) (initMove-2);
            int textBoxStartY = (int) (initEnd-2);
	            
            // Draw Column rectangle
            //log.info("Draw Block "+i+" TextBox="+text+", TextBoxStartX="+textBoxStartX+", TextBoxStartY="+textBoxStartY+", TextBoxWidth="+textBoxWidth+", TextBoxHeight="+textBoxHeight);
            drawRect(contentStream, textBoxColor, new java.awt.Rectangle(textBoxStartX, textBoxStartY, textBoxWidth, textBoxHeight), textBoxFill);

            nextTextX += table.getColumnsStuAss().get(i).getWidth();
            pTrackingForm.setTableStartY(nextTextY);
            //log.info("PDFTableGenerator - writeContentLine - TableStartY="+pTrackingForm.getTableStartY());
        }
        
        if (contentType.equalsIgnoreCase("TableHeader")){
        	//log.info("PDFTableGenerator - writeContentLine - End of Header");
        }else{
        	//log.info("PDFTableGenerator - writeContentLine - End of Content");
        }
    }
    
    private void drawRect(PDPageContentStream content, Color color, Rectangle rect, boolean fill) throws IOException {
        content.addRect(rect.x, rect.y, rect.width, rect.height);
        if (fill) {
            content.setNonStrokingColor(color);
            content.fill();
        } else {
            content.setStrokingColor(color);
            content.stroke();
        }
    }

    private void drawTableGrid(PDFTable table, String[][] currentPageContent, PDPageContentStream contentStream, float tableTopY)
            throws IOException {
        // Draw row lines
        float nextY = tableTopY;
        //log.info("PDFTableGenerator - drawTableGrid - Start tableTopY="+tableTopY);
        //log.info("PDFTableGenerator - drawTableGrid - currentPageContent Length="+currentPageContent.length);
        
        for (int i = 0; i <= currentPageContent.length; i++) {
        	//log.info("PDFTableGenerator - drawTableGrid - For Loop(i)="+i);
        	//log.info("PDFTableGenerator - drawTableGrid - Table Margin="+table.getMargin());
            //contentStream.moveTo(table.getMargin(), nextY);
            contentStream.newLineAtOffset(table.getMargin(), nextY);
            //log.info("PDFTableGenerator - drawTableGrid - Table Width="+table.getWidth());
            contentStream.lineTo(table.getMargin() + table.getWidth(), nextY);
            //log.info("PDFTableGenerator - drawTableGrid - Draw Stroke");
            contentStream.stroke();
            //log.info("PDFTableGenerator - drawTableGrid - nextY="+nextY);
            nextY -= table.getRowHeight();
        }

        //log.info("PDFTableGenerator - drawTableGrid - Draw Column Lines");
        // Draw column lines
        //log.info("PDFTableGenerator - drawTableGrid - getRowHeight="+table.getRowHeight());
        
        final float tableYLength = table.getRowHeight() + (table.getRowHeight() * currentPageContent.length) + 3;
        //log.info("PDFTableGenerator - drawTableGrid - tableYLength="+tableYLength);
        final float tableBottomY = tableTopY - tableYLength;
        //log.info("PDFTableGenerator - drawTableGrid - tableBottomY="+tableBottomY);
        float nextX = table.getMargin();
        //log.info("PDFTableGenerator - drawTableGrid - getMargin="+table.getMargin());
        //log.info("PDFTableGenerator - drawTableGrid - Draw Column Lines - nextX="+nextX+", tableYLength="+tableYLength+", tableBottomY="+tableBottomY);
        for (int i = 0; i < table.getNumberOfColumns(); i++) {
            contentStream.moveTo(nextX, tableTopY);
            contentStream.lineTo(nextX, tableBottomY);
            contentStream.stroke();
            nextX += table.getColumnsStuAss().get(i).getWidth();
        }
        //log.info("PDFTableGenerator - drawTableGrid - Draw Column Lines - Final");
        contentStream.moveTo(nextX, tableTopY);
        contentStream.lineTo(nextX, tableBottomY);
        contentStream.stroke();
        //log.info("PDFTableGenerator - drawTableGrid - Draw Column Lines - End");
    }

    private String[][] getContentForCurrentPage(PDFTable table, Integer rowsPerPage, int pageCount) {
        int startRange = pageCount * rowsPerPage;
        int endRange = (pageCount * rowsPerPage) + rowsPerPage;
        if (endRange > table.getNumberOfRows()) {
            endRange = table.getNumberOfRows();
        }
        //log.info("PDFTableGenerator - getContentForCurrentPage - startRange="+startRange+", endRange="+endRange+", pageCount="+pageCount);
        return Arrays.copyOfRange(table.getContent(), startRange, endRange);
    }

    private PDPage generatePage(PDDocument doc, PDFTable table) {
        PDPage page = new PDPage();
        page.setMediaBox(table.getPageSize());
        page.setRotation(table.isLandscape() ? 90 : 0);
        doc.addPage(page);
        return page;
    }

    private PDPageContentStream generateContentStream(PDDocument doc, PDPage page, PDFTable table) throws IOException {
        PDPageContentStream contentStream = new PDPageContentStream(doc, page);
        // User transformation matrix to change the reference when drawing.
        // This is necessary for the landscape position to draw correctly
        if (table.isLandscape()) {
        	Matrix posMatrix = new Matrix(0, 1, -1, 0, table.getPageSize().getWidth(), 0);
            contentStream.transform(posMatrix);
        }
        //contentStream.setFont(table.getTextFont(), table.getFontSize());
        return contentStream;
    }
}