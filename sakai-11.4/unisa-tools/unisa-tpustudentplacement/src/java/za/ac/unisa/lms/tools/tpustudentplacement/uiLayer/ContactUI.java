package za.ac.unisa.lms.tools.tpustudentplacement.uiLayer;
import java.util.ArrayList;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Address;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Contact;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Supervisor;
import za.ac.unisa.lms.tools.tpustudentplacement.dao.ContactDAO;
public class ContactUI  extends ContactDAO{
    	      public Contact getContactDetails(Integer referenceNo) throws Exception {
                              return getContactDetails(referenceNo,231);	
              }
  public String  getEmailAddress(Integer referenceNo) throws Exception {
                              Contact contact= getContactDetails(referenceNo,231);
                              if(contact==null){
                                      return "";
                              }else{
                                      return  contact.getEmailAddress();
                             }
  }
}