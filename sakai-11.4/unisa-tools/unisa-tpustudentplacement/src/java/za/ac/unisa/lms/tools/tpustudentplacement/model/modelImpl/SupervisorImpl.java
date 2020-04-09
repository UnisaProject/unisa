package za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl;
import java.util.Iterator;
import java.util.List;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.DateUtil;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Province;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Supervisor;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.SupervisorListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.dao.SupervisorDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.uiLayer.ContactUI;
public class SupervisorImpl extends SupervisorDAO{
	
	public String supervisorProvince(List provinces,int supervisorCode){
                     String provinceStr="";
                     if((provinces==null)||(provinces.isEmpty())){
                           return "";
                     }else{
                             Iterator i= provinces.iterator();
                             while (i.hasNext()){
    	                           Province  prov=(Province)i.next();
    	                           provinceStr+=prov.getDescription();
    	                           provinceStr+=(",");
                             }
                             provinceStr=provinceStr.substring(0,(provinceStr.length()-1));
                    }
                    return provinceStr;
     }
     public String getEmailAddress(int code)throws Exception {
                            ContactUI contactUI=new ContactUI();
                        return contactUI.getEmailAddress(code);
     }
     public static int getPosOfSupevisorInList(int supervisorCode,List supervisorList){
                   int pos=-1;
                   if(supervisorList==null){
	                    return pos;
                   }
                   for(int x=0;x<supervisorList.size();x++){
	                    Supervisor  supervisor =(Supervisor)supervisorList.get(x);
                       if(supervisor.getCode()==supervisorCode){
     	                      pos=x; 
    	                      break;
                       }
                   } 
                   return pos;
    }
    public Supervisor getSup(int code)throws Exception {
              Supervisor supervisor= getSupervisor(code);
              DateUtil dateutil=new DateUtil();
              String  totStudentsAllocToSupevisor=getStudentsAllocated(code,dateutil.yearInt());
              supervisor.setStudentsAllocated(totStudentsAllocToSupevisor);
              supervisor.setEmailAddress(supervisor.getEmailAddress(code));
              supervisor.setProvCodesList(getSupervProvList(code));
        return supervisor;
    }
      
    public boolean isExpiredSupervisor(SupervisorListRecord supervisor){
        boolean expired=false;
        if(supervisor.getContract().equals("Y")){
                if(isExpiredSuperv(supervisor)||isExpiringWithinTimeFrame(supervisor,31)){
              	  expired=true;
                }
	    }
	    	  
        return expired;
    }
 public String getSurpervisorName(int code)throws Exception{
                      Supervisor supervisor= getSupervisor(code);
                        return supervisor.getName();           }
 }  
