package za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl;
import za.ac.unisa.lms.tools.tpustudentplacement.dao.StudentDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Address;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Qualification;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Student;
import za.ac.unisa.lms.tools.tpustudentplacement.uiLayer.StringsUtil;
import za.ac.unisa.lms.tools.tpustudentplacement.uiLayer.ContactUI;
import java.util.List;
public class StudentImp extends  StudentDAO{
	                             public StudentImp(Qualification qual,ContactUI contact){
	                            	               this.qual=qual;
                            	               this.contact=contact;
		                      }
	                            Qualification qual;
	                            ContactUI contact;
	                      private void setStuModulesData(Student student, int stuNum,short acadYear,short semester)throws Exception{
	                    	                  List moduleList=getStudentModuleList(stuNum,acadYear,semester);
                	                           student.setListPracticalModules(moduleList);
                                              StringsUtil stringsUtil=new StringsUtil();
                                              String moduleString=stringsUtil.getStringFromList(moduleList);
                                              student.setPracticalModules(moduleString);
              }
              private void setStuAddresses(Student student)throws Exception{
            	                                   Address postalAddress=contact.getAddress(student.getNumber(),1,1);
                                                   Address physicalAddress=contact.getAddress(student.getNumber(),1,3);
                                                   student.setPostalAddress(postalAddress);
                                                   student.setPhysicalAddress(physicalAddress);
              }
               public void setStudentData(Student student, int stuNum,short acadYear,short semester)throws Exception{
            	                           getStudent(student,stuNum);
            	                           student.setQualification(qual);
     	                                   setStuModulesData(student,stuNum,acadYear, semester);
	                                       setStuAddresses(student);
	                                       student.setContactInfo(contact.getContactDetails(stuNum,1)); 
	         }
}
