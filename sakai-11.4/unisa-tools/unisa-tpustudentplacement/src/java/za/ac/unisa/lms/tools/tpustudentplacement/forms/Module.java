package za.ac.unisa.lms.tools.tpustudentplacement.forms;
import javax.servlet.http.HttpServletRequest;

import  za.ac.unisa.lms.tools.tpustudentplacement.dao.ModuleDAO;
public class Module  {
	 private String code;
	 private int level;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
	              	this.code = code;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public Module getModule(Qualification qual,String moduleCode,int acadYear) throws Exception {
		                     ModuleDAO moduleDAO=new  ModuleDAO();
		                     return moduleDAO.getModule(qual,moduleCode,acadYear);
	}
	public  Module(){
	}
    public   Module(Qualification qual,String moduleCode,int acadYear)throws Exception {
	                      ModuleDAO moduleDAO=new  ModuleDAO();
	                      Module module=moduleDAO.getModule(qual,moduleCode,acadYear);
	                       this.code=module.getCode();
	                       this.level=module.getLevel();
	}
    public void checkOnModuleChange(StudentPlacementForm studenPlacementForm, HttpServletRequest request) throws Exception{
    	                   Module   module=new Module();
    	                   String moduleCode=studenPlacementForm.getStudentPlacement().getModule();
                           Qualification qual=studenPlacementForm.getStudent().getQualification();
                           int acadYear=Integer.parseInt(studenPlacementForm.getAcadYear());
                          module=module.getModule(qual,moduleCode,acadYear);
                          int level=module.getLevel();
                          if(module.getLevel()==1){
                        	                studenPlacementForm.setDisplaySecDatesBatch("N");
                          }else{
                        	               studenPlacementForm.setDisplaySecDatesBatch("Y");
                          }
                          studenPlacementForm.setPracticeBatchDateListsIndex(-1);
                          studenPlacementForm.setPracticeBatchDateSecPracPrdListsIndex(-1);
                          studenPlacementForm.getStudentPlacement().setTwoPlacements(false);
                          studenPlacementForm.getStudentPlacement().setEndDate("");
                          studenPlacementForm.getStudentPlacement().setStartDate("");
                          studenPlacementForm.getStudentPlacement().setEndDateSecPracPeriod("");
                          studenPlacementForm.getStudentPlacement().setStartDateSecPracPeriod("");
  	                      if(studenPlacementForm.getIsPGCE().equals("N")){
                         	           if(level>1){
                         	        	       studenPlacementForm.getStudentPlacement().setTwoPlacements(true);
                         	        	       }else{
                       	                    	studenPlacementForm.getStudentPlacement().setDatesToRequest(request);
                       	             }
                          }
  	                      studenPlacementForm.getStudentPlacement().setNumberOfWeeks("");
                          studenPlacementForm.getStudentPlacement().setNumberOfWeeksSecPracPrd("");
                           PracticeDatesMaintenance practiceDatesMaintenance=new PracticeDatesMaintenance();
                          practiceDatesMaintenance.setPracDateBatcheLists(studenPlacementForm);
                          studenPlacementForm.setStudyLevel(module.getLevel());
                         
    }
}