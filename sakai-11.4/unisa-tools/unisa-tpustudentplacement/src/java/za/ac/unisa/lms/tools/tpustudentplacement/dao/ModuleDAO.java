	package za.ac.unisa.lms.tools.tpustudentplacement.dao;
	import java.util.Iterator;
	import java.util.List;
	import org.apache.commons.collections.map.ListOrderedMap;
  import za.ac.unisa.lms.tools.tpustudentplacement.forms.Module;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Qualification;
import za.ac.unisa.lms.db.StudentSystemDAO;
	public class ModuleDAO  extends StudentSystemDAO {
		              
		
		              databaseUtils dbutil;
		              public  ModuleDAO(){
		            	         dbutil=new databaseUtils();
		              }
              public Module getModule(Qualification qual,String moduleCode,int acadYear) throws Exception {
		            	                   Module module= new  Module();
	              		           		   String sql = "select  *  from qspsun  where mk_study_unit_code='"+ moduleCode+"'"+
	              		                                 " and mk_qual_code='"+qual.getCode()+"' and mk_spes_code='"+qual.getSpecializationCode()+
	              		           		                      "'  and ( (from_year<="+acadYear+") or (from_year=0)) "+
	              		                                      " and ( (to_year =0)  or  (to_year>="+acadYear+"))";
	              		                                                     String  errorMsg="Error reading sun / ";
	              		                                           List queryList = dbutil.queryForList(sql,errorMsg);
	              	                                               Iterator i = queryList.iterator();
	              			                                        while (i.hasNext()) {
	              				                                                      ListOrderedMap data = (ListOrderedMap) i.next();
	              				                                                       module.setCode(dbutil.replaceNull(data.get("mk_study_unit_code")).trim());
	              				                                                       String level=dbutil.replaceNull(data.get("study_level")).trim();
	              				                                                        module.setLevel(Integer.parseInt(level));
	              				                                                        break;
	              				                                         }
	              		                                return module;	
	              	 }
	}
		           