package za.ac.unisa.lms.tools.tpustudentplacement.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.SubProvince;

public class SubProvinceDAO {
	                        public SubProvinceDAO(){
	                                             dbutil=new databaseUtils();
                            }
	                        databaseUtils   dbutil;
	                    public List<SubProvince>getSubProvinceList(int provCode) throws Exception{
		                                                               String sql=" select  a.code as subProvCode, a.eng_description as  subProvCodeDescr ,b.eng_description as provDescr  "+
	                                                                                    " from tpusubprv  a,prv  b  where  "+
	                                                                                    " a.fk_prv_code=b.code  and b.code="+provCode;
		                                                               String  errorMsg="database error  when accessing table subProv";
			                                                           List queryList=dbutil.queryForList(sql, errorMsg);
		                                                                List   subProvList=new ArrayList();
                                                                        Iterator i = queryList.iterator();
                                                                        while (i.hasNext()) {
                                                                                     ListOrderedMap data = (ListOrderedMap) i.next();
                                                                                     SubProvince  subProvince=new SubProvince();
                                                                                     subProvince.setCode(Integer.parseInt(dbutil.replaceNull(data.get("subProvCode"))));
                                                                                     subProvince.setDescription(dbutil.replaceNull(data.get("subProvCodeDescr")));
                                                                                     subProvince.setProvinceCode( provCode);
                                                                                     subProvince.setProvinceDescription(dbutil.replaceNull(data.get("provDescr")));
                                                                                     subProvList.add(subProvince);
                                                                          }
                                                                         return subProvList;
		           }
	                    
	               public List<SubProvince>getSubProvinceList() throws Exception{
	           		                                                   String sql=" select   *  from tpusubprv  ";
                                                                       String  errorMsg="database error  when accssing table subProv";
                                                                       List queryList=dbutil.queryForList(sql, errorMsg);
                                                                       List   subProvList=new ArrayList();
                                                                       Iterator i = queryList.iterator();
                                                                       while (i.hasNext()) {
                                                                                            ListOrderedMap data = (ListOrderedMap) i.next();
                                                                                            SubProvince  subProvince=new SubProvince();
                                                                                             subProvince.setCode(Integer.parseInt(dbutil.replaceNull(data.get("code"))));
                                                                                             subProvince.setDescription(dbutil.replaceNull(data.get("eng_description")));
                                                                                             subProvince.setProvinceCode(Integer.parseInt(dbutil.replaceNull(data.get("fk_prov_code"))));
                                                                                              subProvList.add(subProvince);
                                                                        }
                                                                        return subProvList;
          }
		  public SubProvince getSubProvince(int subProvCode)throws Exception{
			                                     	String sql=" select   *  from tpusubprv  where    code="+subProvCode;
                                                    String  errorMsg="database error  when accssing table subProv";
                                                    List queryList=dbutil.queryForList(sql, errorMsg);
                                                    List   subProvList=new ArrayList();
                                                    Iterator i = queryList.iterator();
                                                    while (i.hasNext()) {
                                                                  ListOrderedMap data = (ListOrderedMap) i.next();
                                                                  SubProvince  subProvince=new SubProvince();
                                                                  subProvince.setCode(Integer.parseInt(dbutil.replaceNull(data.get("code"))));
                                                                  subProvince.setDescription(dbutil.replaceNull(data.get("eng_description")));
                                                                  subProvince.setProvinceCode(Integer.parseInt(dbutil.replaceNull(data.get("fk_prov_code"))));
                                                                  subProvList.add(subProvince);
                                                                  break;
                                                   }
                                                   return (SubProvince )subProvList.get(0);
                                                   
        	}
		  public int  getProvCode(int subProvCode)throws Exception{
           	                                 String sql=" select  fk_prv_code  from tpusubprv  where    code="+subProvCode;
                                             String  errorMsg="database error  when accssing table subProv";
                                             return dbutil.queryInt(sql, errorMsg);
		  }
		  public void  delete(int code)throws Exception{
	                                                      String sql=" delete from   tpusubprv     where    code="+code;
                                                          String  errorMsg="database error  when accessing table tpuSubProv";
                                                         dbutil.update(sql, errorMsg);
          }
			public void update(int code,String name)throws Exception{
                                                         String sql=" update  tpusubprv set  eng_description='"+name+"'  where    code="+code;
                                                         String  errorMsg="database error  when accssing table tpusubPrv";
                                                        dbutil.update(sql, errorMsg);
         }
		  public void insert(SubProvince subProvince) throws Exception {
				                  	                         String sql = "insert into tpusubprv (code,eng_description,fk_prv_code,in_use_flag) " +
					                                                            " values " ;
				                  	                          if (dbutil.isEmptyTablet("code","tpusubprv")){
				                  	                        	                      sql +="(30,";
				                  	                          }else{
				                  	                        	                      sql += "((select max(code) + 1 from tpusubprv)," ;
				                  	                          }
				                  	                    	  sql =sql+	"'" + subProvince.getDescription().toUpperCase().trim() + "'," +subProvince.getProvinceCode()+ ",'Y')";
				                                                dbutil.update(sql,"SubProvinceDao : Error inserting  tpusubprv " );	
			  }
			 
}