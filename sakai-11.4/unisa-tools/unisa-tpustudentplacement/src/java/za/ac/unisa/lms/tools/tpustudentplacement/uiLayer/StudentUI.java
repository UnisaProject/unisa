package za.ac.unisa.lms.tools.tpustudentplacement.uiLayer;

import za.ac.unisa.lms.tools.tpustudentplacement.forms.Contact;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Student;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
public class StudentUI {
	
		   public void setStudentCellNrToFormBean(StudentPlacementForm studentPlacementForm)throws Exception{
			                 String cellNumber=studentPlacementForm.getStudent().getCellNumber();    
			                 studentPlacementForm.setCommunicationCellNumber(cellNumber);
             }
		   public void  setStudentCommunicationDetToFormBean(StudentPlacementForm studentPlacementForm) throws Exception{
                                          String email=studentPlacementForm.getStudent().getStudentEmail();
	                                      studentPlacementForm.setCommunicationEmailAddress(email);
	                                      ContactUI contactUI=new ContactUI();
		                                  Contact contact = contactUI.getContactDetails(studentPlacementForm.getStudent().getNumber(), 1);
	                                      studentPlacementForm.setCommunicationCellNumber(contact.getCellNumber());
           }
				public String getStudentContactNumber(StudentPlacementForm studentPlacementForm) throws Exception{
				                              String contactNumber=studentPlacementForm.getStudent().getContactInfo().getHomeNumber();
				                              return contactNumber;
			  }
				  public Contact getContactDetails(String stuNum)throws Exception {
		    	            int studentNr=Integer.parseInt(stuNum);
		    	            ContactUI contactUI=new ContactUI(); 
		    	          return contactUI.getContactDetails(studentNr, 1);
               
		    }
	        public void  setStudentEmailToFormBean(StudentPlacementForm studentPlacementForm) throws Exception{
	        	                          String stuEmailAddress=studentPlacementForm.getStudent().getStudentEmail();
	        	                          studentPlacementForm.setCommunicationEmailAddress(stuEmailAddress);
	         }
	         public void  setStudent(StudentPlacementForm studentPlacementForm)throws Exception {
			                            int stuNum=Integer.parseInt(studentPlacementForm.getStudentNr());
	    	                           short semester=Short.parseShort(studentPlacementForm.getSemester());
	    	                           short academicYear=Short.parseShort(studentPlacementForm.getAcadYear());
	    	                           Student student=new Student(stuNum,academicYear);
	    	                           if(student.getNumber()==null){
	    	                                           student.setNumber(-1);   
	       	                           }
	    	                           studentPlacementForm.setStudent(student);
  }
}