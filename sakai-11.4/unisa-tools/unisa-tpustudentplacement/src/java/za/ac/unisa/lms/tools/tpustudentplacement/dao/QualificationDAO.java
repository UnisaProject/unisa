package za.ac.unisa.lms.tools.tpustudentplacement.dao;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

import za.ac.unisa.lms.tools.tpustudentplacement.forms.Qualification;

public class QualificationDAO {
 	                    databaseUtils dbutil;
                        public QualificationDAO(){
                                          dbutil=new databaseUtils();
                        }
                     	public  boolean isPGCE(String qualCode)throws Exception{
                        	                                  String query="Select type from grd where code='"+qualCode+"'";
                        	                                  String errorMsg="QualificationDAO:Error reading grd ";
	                                                          String type=dbutil.querySingleValue(query,"type",errorMsg);
	                                                           if(type.equals("C")){
	            	                                                        return  true;
	                                                           }else{
	            	                                                         return false;
	                                                           }
                      }
                     	 public Qualification getStudentQual(int studentNr, Short acadYear) throws Exception {
                     		
	                         Qualification qual = new Qualification();
			                 String sql = "select b.type as qualType,a.mk_highest_qualifi as qualCode, b.eng_description as qualDesc, b.short_description as qaulShortDesc" + 
	                                      " from stuann a,grd b" +
	                                      " where a.mk_student_nr=" + studentNr +
	                                      " and a.mk_academic_year=" + acadYear +
	                                      " and a.mk_academic_period=1" +
	                                      " and a.mk_highest_qualifi=b.code";
  	                                      databaseUtils dbutil=new databaseUtils();
  	                                      String errorMsg="Error reading stuan, grd tables, in StudentDAO";
		                                  List queryList = dbutil.queryForList(sql,errorMsg);
		                                  Iterator i = queryList.iterator();
		                                   while (i.hasNext()) {
			                                       ListOrderedMap data = (ListOrderedMap) i.next();
			                                       qual.setCode(dbutil.replaceNull(data.get("qualCode")));
			                                       qual.setDescription(dbutil.replaceNull(data.get("qualDesc")));
			                                       qual.setShortDesc(dbutil.replaceNull(data.get("qaulShortDesc")));
			                                       qual.setType(dbutil.replaceNull(data.get("qualType")));
		                                    }
	                               return qual;
	
      }
}
