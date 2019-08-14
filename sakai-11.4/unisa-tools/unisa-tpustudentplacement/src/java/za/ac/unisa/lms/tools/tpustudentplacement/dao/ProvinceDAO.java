package za.ac.unisa.lms.tools.tpustudentplacement.dao;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.util.LabelValueBean;
import org.springframework.jdbc.core.JdbcTemplate;
import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Country;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Province;

public class ProvinceDAO extends StudentSystemDAO {
	private  final String sqlForProvList= "select code, eng_description as description , in_use_flag" +
	        "  from prv a  where in_use_flag = 'Y'   and  code not in (select  fk_prv_code   from tpusubPrv  )" +
	        "  order by eng_description"+
	        "  union select code, description, in_use_flag" +
	         "  from tpusubPrv where in_use_flag = 'Y'" +
	         "  order by description";
	private  final String sqlForProvExSubPrvList= "select code, eng_description  , in_use_flag" +
	        "  from prv  where in_use_flag = 'Y'   " +
	        "  order by eng_description";
	
	 databaseUtils dbutil;
       public ProvinceDAO(){
                dbutil=new databaseUtils();
     }   
       
	public List getProvinceList() throws Exception {
		                    	return getProvinceListFromDatabase(sqlForProvList,"Error reading prv  and  tpusubprv, ProvinceDAO") ;
	}
	public List getProvinceListExSubPrv() throws Exception {
                         return getProvinceListFromDatabase(sqlForProvExSubPrvList,"Error reading prv, ProvinceDAO") ;
   }
	public List getProvinceListFromDatabase(String sql,String errorMsg) throws Exception {
                                       List listProvince  = new ArrayList<Province>();
                                       List queryList = new ArrayList();
	                                   Province province = new Province();	
    	                               queryList =  dbutil.queryForList(sql,errorMsg);
                                       for (int i=0; i<queryList.size();i++){
                                                        province = new Province();				
                                                        ListOrderedMap data = (ListOrderedMap) queryList.get(i);
                                                       province.setCode(Short.parseShort((String)data.get("code").toString()));
                                                       province.setDescription(data.get("description").toString());
                                                       province.setIn_use(data.get("in_use_flag").toString());
                                                       listProvince.add(province);						
                                      }
                                     return listProvince;	
     }
	public List getProvinceLabelValueList(String defaultValue,String defaultString) throws Exception {
		                                List listProvince=getProvinceLabelValueListFromDatabase(sqlForProvList,"Error reading prv, ProvinceDAO");
		                		        listProvince.add(0,new LabelValueBean(defaultValue,defaultString));
		                		        return   listProvince;
		                	    
	}
	public List getProvinceExSubPrvLabelValueList() throws Exception {
		                                return getProvinceLabelValueListFromDatabase(sqlForProvExSubPrvList,"Error reading prv, ProvinceDAO");
		    }
		public List getProvinceLabelValueListFromDatabase(String sql,String errorMsg) throws Exception {
        List listProvince  = new ArrayList<Province>();
        List queryList = new ArrayList();
        String value="";
		String label="";
	    Province province = new Province();	
        queryList =  dbutil.queryForList( sql,errorMsg);
        for (int i=0; i<queryList.size();i++){
                         province = new Province();				
                         ListOrderedMap data = (ListOrderedMap) queryList.get(i);
                        province.setCode(Short.parseShort((String)data.get("code").toString()));
                        province.setDescription(data.get("description").toString());
                        province.setIn_use(data.get("in_use_flag").toString());
                        value=""+province.getCode();
        				listProvince.add(new LabelValueBean(label, value));		
       }
          return listProvince;	
}
	public String getProvinceDescription(int code)throws Exception {
		                                       String sql ="";
		                                       if(databaseUtils.saCode.equals(code)){
		                                    	        return "";
		                                       }
                                               if(Province.isProvince(code)){
		                                                       sql = "select eng_description  as description  from prv where code="+code;
                                               }else{
                                        	                    sql = "select  eng_description from tpusubPrv where code="+code;
                                              }
		                                      databaseUtils  databaseutils=new databaseUtils();
		                                      String errorMsg="";
		                                      if(Province.isProvince(code)){
		                                    	errorMsg=" ProvinceDAO:error reading prv table";
			                                                  return databaseutils.querySingleValue(sql,"eng_description", errorMsg);
		                                      }else{
		                                    	  errorMsg=" ProvinceDAO:error reading tpusubprv table";
		                                    	             return databaseutils.querySingleValue(sql,"eng_description", errorMsg);
		                                      }
	}
}
