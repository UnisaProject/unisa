package za.ac.unisa.lms.tools.tpustudentplacement.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.util.LabelValueBean;
import org.springframework.jdbc.core.JdbcTemplate;
import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.District;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Province;

public class DistrictDAO extends StudentSystemDAO {
	                  
	                   databaseUtils dbutil;
  	                   public DistrictDAO(){
                                  dbutil=new databaseUtils();
                       }   
           public void updateDistrict(District district) throws Exception {
		 		                                          String sql = "update tpuSchDistrict  set description='" + district.getDescription().toUpperCase().trim() + "'," +
			                                                                " mk_prv_code=" + district.getProvince().getCode() + "," + 
			                                                                " fk_subPrv_code=" + district.getSubProvCode()+ "," + 
			                                                                " in_use_flag='" + district.getInUse().toUpperCase().trim() + "'" +
			                                                                " where code=" + district.getCode();
		 		                                            String errorMsg="StudentPlacementDao : Error updating TPUDIS / ";
		 		                                           dbutil.update(sql,errorMsg);	
		              }
		               public void linkToSubProv(Short districtCode,Short subProvCode) throws Exception {
		                                                      String sql = "update ldd set  fk_TpuSubPrv_code=" + subProvCode + "  where code=" + districtCode;
		                                                      String errorMsg="StudentPlacementDao : Error updating ldd / ";
		                                                      if(!isDistrictLinkedToSubProv (districtCode,subProvCode)){
		                                                                     dbutil.update(sql,errorMsg);	
		                                                      }
                       }
	                   public void unlinkToSubProv(Short districtCode) throws Exception {
                                                          String sql = "update ldd set  fk_TpuSubPrv_code=null  where code=" + districtCode;
                                                          String errorMsg="StudentPlacementDao : Error updating ldd / ";
                                                          dbutil.update(sql,errorMsg);	
                      }
	                   public void unlinkAllDistrictsLinkedToSubProv(int subProvCode) throws Exception {
                                                         String sql = "update ldd set  fk_TpuSubPrv_code=null  where fk_TpuSubPrv_code=" + subProvCode;
                                                         String errorMsg="StudentPlacementDao : Error updating ldd / ";
                                                         dbutil.update(sql,errorMsg);	
                     }
	                   public void linkToSubProv(List<District> districtList,Short subProvCode) throws Exception {
	                	                               Iterator iter=districtList.iterator();
	                	                                while(iter.hasNext()){
	                	                                	   District district=(District)iter.next();
	                	                                	             linkToSubProv( district.getCode(),subProvCode);
	                	                               }
                      }
	                  public District getDistrict(Short code, String description) throws Exception {
		                                  District district = new District();
		                                  district.setDescription("not found");
		                                  String sql = "select code,eng_description,fk_prvcode,fk_TpuSubPrv_code,in_use_flag from ldd";
		          		                  if (code!=null && code!=0){
			                                    sql = sql + " where code=" + code;
		                                  }else if (description!=null && !description.equalsIgnoreCase("")){
			                                        sql = sql + " where eng_description ='" + description.toUpperCase().trim() + "'";
		                                  }		
		          		                  String errorMsg="DistrictDao : Error reading table ldd / "; 
		          		                   List queryList = dbutil.queryForList(sql,errorMsg);
					                      Iterator i = queryList.iterator();
			                              while (i.hasNext()) {
				                                           ListOrderedMap data = (ListOrderedMap) i.next();
				                                           Province province = new Province();
								                           district.setCode(Short.parseShort(data.get("code").toString()));
				                                           district.setDescription(dbutil.replaceNull(data.get("eng_description")));
				                                           province.setCode(Short.parseShort(data.get("fk_prvcode").toString()));
				                                           district.setProvince(province);		
	                                                       district.setInUse(dbutil.replaceNull(data.get("in_use_flag")));
	                                                       String subProvCodeStr=dbutil.replaceNull(data.get("fk_TpuSubPrv_code"));
				                                           if(subProvCodeStr.trim().equals("")){
				                                	                         subProvCodeStr="0";
				                                           }
				                                          district.setSubProvCode(Short.parseShort(subProvCodeStr));
				                                          break;
			                             }
               		   		             return district;		
	                 }
	                  
	                  public List<District> getDistrictList(Short provinceCode) throws Exception {
                                                                    String sql = "select a.code as districtCode,a.eng_description  as districtDescr,a.fk_prvcode  as provCode,b.eng_description as provinceDescr, "+
                                                                       " (decode (a.fk_TpuSubPrv_code ,null,' ',(select  eng_description as subprovDescr from  TpuSubPrv where  TpuSubPrv.code=a.fk_TpuSubPrv_code) )) "+
                                                                       " as subProvinceDescr ,a.in_use_flag  as in_use_flag,a.fk_TpuSubPrv_code  as  fk_TpuSubPrv_code from ldd a,prv b"+
                                                                       "  where a.fk_prvcode=b.code";
  		                                              if (provinceCode!=null && provinceCode!=0){
                                                                         sql = sql + " and  b.code=" +  provinceCode;
                                                       }  
  		                                               String errorMsg="DistrictDao : Error reading table ldd / "; 
  		                                                List queryList = dbutil.queryForList(sql,errorMsg);
  		                                              List   districtList=new ArrayList<District>();
	                                                    Iterator i = queryList.iterator();
                                                        while (i.hasNext()) {
                                                                           ListOrderedMap data = (ListOrderedMap) i.next();
                                                                           District district = new District();
                                                                           Province province = new Province();
				                                                          district.setCode(Short.parseShort(data.get("districtCode").toString()));
                                                                          district.setDescription(dbutil.replaceNull(data.get("districtDescr")));
                                                                          province.setCode(Short.parseShort(data.get("provCode").toString()));
                                                                          province.setCode(provinceCode);
                                                                         district.setInUse(dbutil.replaceNull(data.get("in_use_flag")));
                                                                          district.setProvinceName(dbutil.replaceNull(data.get("provinceDescr")));		
                                                                          String subProvCodeStr=dbutil.replaceNull(data.get("fk_TpuSubPrv_code"));
               				                                             if(subProvCodeStr.trim().equals("")){
               				                                	                         subProvCodeStr="0";
               				                                             }
               				                                             district.setSubProvCode(Short.parseShort(subProvCodeStr));
               				                                            district.setSubProvince(data.get("subProvinceDescr").toString());
               	                                                         districtList.add(district);
                                                        }
		   		                             return districtList;		
                    }
	                public boolean  isDistrictLinkedToSubProv (Short code, Short subProvCode) throws Exception {
                                                            District district = new District();
                                                            district.setDescription("not found");
                                                            String sql = "select count(*)   from ldd"+
                                                                              "  where code=" + code+" and fk_TpusubPrv_code="+subProvCode;
                                                           String errorMsg="DistrictDao : Error reading table ldd / "; 
                                                           int totRows = dbutil.queryInt(sql, errorMsg);
                                                          if(totRows==0){
                                                        	      return false;
                                                          }
                                                          return true;
     }
		private   static final String districtListForProvSql= "select a.code as districtCode, a.eng_description as districtDesc," +
		                                                                           	"  a.fk_prvcode as provCode,a.in_use_flag as districtInUse, b.eng_description as provDesc" +
		                                                                       	   "  from ldd a,prv b  where a.fk_prvcode = b.code  and   a.fk_TpuSubPrv_code is null";
	private   static final String districtListForSubProvSql = "  union  select a.code as districtCode, a.eng_description as districtDesc," +
                                                                                             " b.code  as provCode,a.in_use_flag as districtInUse, b.eng_description as provDesc" +
                                                                                             "  from ldd a,tpusubPrv b  where a.fk_TpuSubPrv_code = b.code";
	public String lastpartOfdistrictListSql(String inUseFlag, Short provCode, String filter) throws Exception {
                                                         String sql="";
                                                         if (inUseFlag != null && inUseFlag.equalsIgnoreCase("Y")){
                                                                           sql = sql + " and a.in_use_flag='Y'";
                                                        }
	                                                    if(provCode!=null && provCode!=0){
                                                                           sql = sql + " and b.code=" + provCode;					
                                                          }
	                                                     if (filter!=null && !filter.trim().equalsIgnoreCase("")){
                                                                           filter = filter.replaceAll("'", "''");
                                                                            sql = sql + " and a.eng_description like '" + filter.trim().toUpperCase() + "'";
                                                         }
                                                            return sql;
  }
	private List getResultSetOfDitricts(String inUseFlag, Short provCode, String filter) throws Exception {
		                                                            String sql = "";
		                                                            String errorMsg="Database error, error accesing ldd,prv,TpuSubPrv  tables in DitrictDAO";
                                  	                                sql+= districtListForProvSql;
                                  	                                sql+=lastpartOfdistrictListSql(inUseFlag,provCode,filter);
                                                                    sql+=districtListForSubProvSql;
                                                                   sql+=lastpartOfdistrictListSql(inUseFlag,provCode,filter);
                                                                   sql += "  order by districtDesc";
                                                         return  dbutil.queryForList(sql,errorMsg);
   	}
	public List getDistrictList(String inUseFlag, Short provCode, String filter) throws Exception {
		                          List listDistrict  = new ArrayList<District>();
		                          District district = new District();	
		                          List  districtResultSet=getResultSetOfDitricts(inUseFlag,provCode,  filter);
		                         Iterator iter=districtResultSet.iterator();
		                         while(iter.hasNext()){
			                                 district = new District();	
		                                   	Province province = new Province();	
			                                ListOrderedMap data = (ListOrderedMap)  iter.next();
			                                district.setCode(Short.parseShort(data.get("districtCode").toString()));
			                                district.setDescription(data.get("districtDesc").toString());
			                                district.setInUse(data.get("districtInUse").toString());
			                                province.setCode(Short.parseShort(data.get("provCode").toString()));
			                                province.setDescription(data.get("provDesc").toString());
			                                district.setProvince(province);
			                                 listDistrict.add(district);						
			                    }
		                        return listDistrict;			
	}
		public List getDistrictList2(String inUseFlag, Short provCode, String filter) throws Exception {
	                                            	List listDistrict  = new ArrayList<District>();
		                                            List  districtResultSet=getResultSetOfDitricts(inUseFlag,provCode,  filter);
		                                            String value="";
		                                            String label="";
	                                             	String districtCode="";
		                                            String districtDesc="";
	                                            	String provinceCode="";
	                                            	String provinceDesc="";
	                                            	  Iterator iter=districtResultSet.iterator();
	                 		                         while(iter.hasNext()){
	                 			                   	
		                                                               	ListOrderedMap data = (ListOrderedMap)   iter.next();
		                                                            	districtCode = data.get("districtCode").toString();
			                                                            districtDesc = data.get("districtDesc").toString();
			                                                            provinceCode = data.get("provCode").toString();
			                                                            provinceDesc = data.get("provDesc").toString();
			                                                            label = districtDesc;
			                                                            if(provCode==null || provCode==0){
				                                                                          label = label + "---" + provinceDesc;
			                                                            }			
		                                                               value=districtCode + "-" + provinceCode;
			                                                           listDistrict.add(new LabelValueBean(label, value));
			                                       }                   
		                                           return listDistrict;			
	}
		
	
	public void insertDistrict(District district) throws Exception {
		
		String sql = "insert into tpudis (code,description,mk_prv_code,in_use_flag) " +
			"values " +
			"((select max(code) + 1 from tpudis)," + 
			"'" + district.getDescription().toUpperCase().trim() + "'," +
			district.getProvince().getCode()+ "," +
			"'" + district.getInUse().toUpperCase().trim() + "')";
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			int result = jdt.update(sql);	
		}
		catch (Exception ex) {
			throw new Exception("StudentPlacementDao : Error inserting TPUDIS / " + ex,ex);
		}	
	}
	

}
