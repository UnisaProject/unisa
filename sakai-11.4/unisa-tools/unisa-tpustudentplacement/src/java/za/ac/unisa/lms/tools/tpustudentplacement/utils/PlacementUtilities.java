package za.ac.unisa.lms.tools.tpustudentplacement.utils;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacement;

public class PlacementUtilities {
	 public boolean isValidDate(String inDate, String format) {

		    if (inDate == null)
		      return false;

		    //set the format to use as a constructor argument
		    SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		    
		    if (inDate.trim().length() != dateFormat.toPattern().length())
		      return false;

		    dateFormat.setLenient(false);
		    
		    try {
		      //parse the inDate parameter
		      dateFormat.parse(inDate.trim());
		    }
		    catch (ParseException pe) {
		      return false;
		    }
		    inDate.indexOf("/");
		    if (inDate.indexOf("/")!=4 || inDate.length()!=10){
		    	return false;
		    }
		    inDate = inDate.substring(inDate.indexOf("/")+1);
		    if (inDate.indexOf("/")!=2 || inDate.length()!=5){
		    	return false;
		    }
		    return true;
	}
	 
	 public Integer getDefaultAcadYear() {
			return Calendar.getInstance().get(Calendar.YEAR);
		} 
	 
	 public boolean isInteger(String stringValue) {
			try
			{
				Integer i = Integer.parseInt(stringValue);
				return true;
			}	
			catch(NumberFormatException e)
			{}
			return false;
		}
	 public boolean isStringEmpty(String str){
        
         if(str==null ||str.equals("")){
      	      return true;
         }else{
        	    return false;
         }
	 }
	 private static final String saCode="1015";
	 public static String getSaCode(){
		 return saCode;
	 }
	 public static boolean isInt(String stringValue) {
			try
			{
				Integer i = Integer.parseInt(stringValue);
				return true;
			}	
			catch(NumberFormatException e)
			{}
			return false;
		}
	 public void setPlacementDateToRequestObject( HttpServletRequest request,StudentPlacement placement){
	                                                DateUtil dateUtil=new DateUtil();
	                                                request.setAttribute("startDate",placement.getStartDate());
	                                                setPracDateToRequestObject("startDate",placement.getStartDate(),request);
	                                                setPracDateToRequestObject("endDate",placement.getEndDate(),request);
	                                                setPracDateToRequestObject("startDateSecPrd",placement.getStartDateSecPracPeriod(),request);
	                                                setPracDateToRequestObject("endDateSecPrd",placement.getEndDateSecPracPeriod(),request);

     }
	 public void setSecPrdPlacementDateToRequestObject( HttpServletRequest request,StudentPlacement placement){
                                                DateUtil dateUtil=new DateUtil();
                                                setPracDateToRequestObject("startDateSecPrd",placement.getStartDateSecPracPeriod(),request);
                                                setPracDateToRequestObject("endDateSecPrd",placement.getEndDateSecPracPeriod(),request);
     }
	 public void setPrdPlacementDateToRequestObject( HttpServletRequest request,StudentPlacement placement){
		                                        setPracDateToRequestObject("startDate",placement.getStartDate(),request);
	                                            setPracDateToRequestObject("endDate",placement.getEndDate(),request);
	 }
	 
	  
     public void setPracDateToRequestObject(String  requestVar,String value ,HttpServletRequest request){
                                                      DateUtil dateUtil=new DateUtil();
                                                      if(value==null){
                                                                     value=dateUtil.dateOnly();
                                                       }
                                                       request.setAttribute(requestVar,value);
     }
 	 
}