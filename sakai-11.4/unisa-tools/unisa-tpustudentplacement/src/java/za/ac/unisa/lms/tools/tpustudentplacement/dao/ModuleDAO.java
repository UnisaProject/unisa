	package za.ac.unisa.lms.tools.tpustudentplacement.dao;
	import java.util.Iterator;
	import java.util.List;
	import org.apache.commons.collections.map.ListOrderedMap;
  import za.ac.unisa.lms.tools.tpustudentplacement.forms.Module;
     import za.ac.unisa.lms.db.StudentSystemDAO;
	public class ModuleDAO  extends StudentSystemDAO {
		              
		
		              databaseUtils dbutil;
		              public  ModuleDAO(){
		            	         dbutil=new databaseUtils();
		              }
		              public Module getModule(String  code) throws Exception {
	                		
	              		                                         Module module= new  Module();
	              		           		                         String sql = "select  *  from sun  where code='"+ code.trim()+"'";
	              		                                          String  errorMsg="Error reading sun / ";
	              		                                           List queryList = dbutil.queryForList(sql,errorMsg);
	              	                                               Iterator i = queryList.iterator();
	              			                                        while (i.hasNext()) {
	              				                                                      ListOrderedMap data = (ListOrderedMap) i.next();
	              				                                                       module.setCode(dbutil.replaceNull(data.get("code")).trim());
	              				                                                       String level=dbutil.replaceNull(data.get("study_level")).trim();
	              				                                                        module.setLevel(Integer.parseInt(level));
	              				                                                        break;
	              				                                         }
	              		                                return module;	
	              	 }
	}
		           