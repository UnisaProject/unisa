package za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl;
import za.ac.unisa.lms.tools.tpustudentplacement.dao.StudentPlacementLogDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacement;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.studentPlacementImpl.StudentPlacementImage;

public class PlacementLogAdderClass {
	
	      StudentPlacementLogDAO dao;
	      private int personnelNum;
	      private int academicYear;
	      private int semester;
	      private String emailAddress;
	      private String cellNum;
	      private StudentPlacement sp;
	      public  PlacementLogAdderClass(){ 
	    	      dao=new StudentPlacementLogDAO();
	      }
	      private String  getImageForCreatePlacement(StudentPlacement spl){ 
		                String afterImage="";
                        try{
        	                   StudentPlacementImage placementImage=new StudentPlacementImage(spl);
                               afterImage=placementImage.getPlacementImage();
                               return afterImage;
                         }catch(Exception ex){
        	                       return afterImage=""; 
                         }
            }
	        public void setLogOnNewPlacement(StudentPlacementForm studentPlacementForm)throws Exception{
            	             initialiseVariasbles(studentPlacementForm);
            	             String afterImage=getImageForCreatePlacement(sp);
                             dao.addLogForCreateNewPlacementAction(sp,academicYear,semester,afterImage,personnelNum);
           }
	        /* Adding a log after deleting previous student placements for the current year, will require the personnel number, here a question is,
	         * who is removing the placement in the first place, because the user's action at this point is that they made a new   placement ,not using  Date Blocks,
	         * and the system is the one thAt deleted the placements.Using the user's personnel number in the log, will be  questionable,a counter argument will be 
	         * the following,the user should have deleted the placements in the first place and the system is doing what the user should have done in the first place,and therefore,
	         * it sounds acceptable to use  their personnel number in the log after deleting  the above-mentioned placements.I decided to use a code 000000  to represent 
	         * that the system was responsible for the delete
	         * A log is added to the database  table  for each student placement 
	         */
                 public void setLogOnDeletePlacement(StudentPlacementForm studentPlacementForm,int personnelNum)throws Exception{
                                     initialiseVariasbles(studentPlacementForm,personnelNum);
                                    String afterImage=getImageForCreatePlacement(sp);
                                    dao.addLogForDeletePlacementAction(sp,academicYear,semester,afterImage, personnelNum);
               }
                 public void setLogOnDeletePlacement(StudentPlacementForm studentPlacementForm)throws Exception{
                	                                           int persno=Integer.parseInt(studentPlacementForm.getPersonnelNumber());
                                                               initialiseVariasbles(studentPlacementForm,persno);
                                                               String afterImage=getImageForCreatePlacement(sp);
                                                               dao.addLogForDeletePlacementAction(sp,academicYear,semester,afterImage, personnelNum);
                 }
                  public void setLogOnEmailToStu(StudentPlacementForm studentPlacementForm)throws Exception{
            	                               initialiseVariasbles(studentPlacementForm);
            	                              String afterImage=getImageForCreatePlacement(sp);
                                              dao.setLogOnEmailToStu(sp,academicYear,semester,emailAddress,afterImage,personnelNum);
	             }
                 public void setLogOnSmsToStu(StudentPlacementForm studentPlacementForm)throws Exception{
            	                            initialiseVariasbles(studentPlacementForm);
            	                            String afterImage=getImageForCreatePlacement(sp);
                                           dao.setLogOnSmsToStu(sp,academicYear,semester,cellNum,afterImage,personnelNum);
            }
            public void setLogOnEmailToSchool(StudentPlacementForm studentPlacementForm)throws Exception{
            	                           initialiseVariasbles(studentPlacementForm);
            	                          String afterImage=getImageForCreatePlacement(sp);
                                          dao.setLogOnEmailToSchool(sp,academicYear,semester,emailAddress,afterImage,personnelNum);  
           }
            public void setLogOnUpdatePlacement(StudentPlacementForm studentPlacementForm)throws Exception{
            	                              initialiseVariasbles(studentPlacementForm);
            	                              String placementImage=studentPlacementForm.getPlacementImage();
            	                              String afterImage=getImageForCreatePlacement(sp);
                                              String beforeImage=placementImage;//dao.getPlacementImage(placementImageInteger.parseInt(sp.getStuNum()),sp.getSchoolCode(),sp.getModule());
                                             dao.addLogForUpdatePlacementAction(sp,academicYear,semester, beforeImage, afterImage, personnelNum);
           }
           private void initialiseVariasbles(StudentPlacementForm studentPlacementForm){
        	                                    personnelNum=Integer.parseInt(studentPlacementForm.getPersonnelNumber());
                                                semester=Integer.parseInt(studentPlacementForm.getSemester());
                                                academicYear=Integer.parseInt(studentPlacementForm.getAcadYear());
                                                emailAddress=studentPlacementForm.getCommunicationEmailAddress();
                                                cellNum=studentPlacementForm.getCommunicationCellNumber();
                                                sp=studentPlacementForm.getStudentPlacement();
                                                sp.setStuNum(studentPlacementForm.getStudentNr());
           }
           private void initialiseVariasbles(StudentPlacementForm studentPlacementForm, int  personnelNum){
        	                             initialiseVariasbles(studentPlacementForm);
        	                             this.personnelNum=personnelNum;
          }

               
}
