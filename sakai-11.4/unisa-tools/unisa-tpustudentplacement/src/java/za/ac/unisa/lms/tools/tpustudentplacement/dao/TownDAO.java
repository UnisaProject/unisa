package za.ac.unisa.lms.tools.tpustudentplacement.dao;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.collections.map.ListOrderedMap;
import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.District;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.SchoolListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.SubProvince;
import za.ac.unisa.lms.tools.tpustudentplacement.model.Town;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.DistrictUI;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.PlacementUtilities;

public class TownDAO extends StudentSystemDAO {
	                    databaseUtils dbUtil;
	                       	public TownDAO(){
                      	           	   dbUtil=new databaseUtils();
                         } 
             private String getSqlForSelectingnTowns(String country,Short province,Short district)throws Exception {
                      		                                          String  sql="" ,lastPartOfSql="";
                      		                                          lastPartOfSql=  getLastPart(country, province,district);
                      		                                              sql ="select  distinct(town) as nameOfTown from tpusch  a,adr b,ldd c  "+
                      		                                    			  "  where   a.code=b.reference_no  and a.mk_district_code=c.code "+
                      		                                    		     "  and town in(select distinct(town) as town from pstcod where type ='B' )"+
                      		                                    		      "  and town not in (select eng_description as prov  from prv) "+
                      		                                    			    lastPartOfSql+"  order by nameOfTown ";
                      		                                                return sql;
            	}
               String postCodesStr="9_2495-2899+5_2900-4730_4740-4799+4_4735-4739_4800-4899_4920-6499"+
            	                                   "+1_6500-8179+2_8180-8999+3_9300-9409_9410-9999+6_1022-1399_2200-2494+7_0699-0999+8_0001-204_"+
            	                                   "1000-1005_1020-1021_1026_1028_1400-1699_1438-1444_1700-1799_1800-1870_1871-1990_2000-2199";
                private String getProvCodeString(short provCode){
            	                                StringTokenizer strTokenizer=new StringTokenizer(postCodesStr,"+");
            	                                String provPostCodeStr="";
            	                                while(strTokenizer.hasMoreTokens()){
            	                            	           provPostCodeStr=strTokenizer.nextToken();
            	                            	           if(provCode==Short.parseShort(""+provPostCodeStr.charAt(0))){
            	                            	        	              break;
            	                            	           }
            	                                }
            	                                return provPostCodeStr;
                }
                private String getPostCodeEliminatorStr(short provCode){
                	                         String provPostCodeStr=getProvCodeString(provCode);
                                             StringTokenizer strTokenizer=new StringTokenizer(provPostCodeStr.substring(2),"_");
                                             String provPostRangeStr="";
                                             String postCodeEliminatorStr=" and (  ";
                                            while(strTokenizer.hasMoreTokens()){
                                            	              provPostRangeStr=strTokenizer.nextToken();
                                            	              postCodeEliminatorStr+=" (postal_code  between "+Integer.parseInt(provPostRangeStr.substring(0,provPostRangeStr.indexOf('-')))+
                                            	        		                                    "  and  "+Integer.parseInt(provPostRangeStr.substring(provPostRangeStr.indexOf('-')+1))+")";
                                            	              if (strTokenizer.hasMoreTokens()){
                                            	            	          postCodeEliminatorStr+=" or ";
                                            	              }else{
                                            	            	          postCodeEliminatorStr+=")";
                                            	              }
                                             }
                                      return postCodeEliminatorStr;
                }
                private String    getLastPart(String country,Short province,Short district)throws Exception {
                	                                                        String lastPartOfSql="";
                	                               	                          if (country!=null &&(!country.trim().equals("0"))&&(!country.trim().isEmpty() )){
                			                                                                lastPartOfSql+=" and a.mk_country_code = '" + country + "'";
                		                                                      }
                	                               	                       if((country!=null)&&country.equals(PlacementUtilities.getSaCode())){  
                	                               	                    	                   if (province!=null &&(province.compareTo(Short.parseShort("0"))!=0)){
          	                                                                                           if(province>=30){
          	                                                                                        	          short subProvCode=province;
          	                                                                                        	          SubProvince subProv=new SubProvince();
          	                                                                                                       short provinceCode=(short)subProv.getProvCode(subProvCode);
          	                                                                                                      lastPartOfSql+=  ( "  and c.fk_tpusubprv_code=" + subProvCode);
                                                          	                        	                           lastPartOfSql+=  ("    and a.mk_prv_code = " + provinceCode); 
          	                                                                                                       String subProvDistCodesStr=getSubProvDistCodes(subProvCode,provinceCode);
          	                                                                                                       lastPartOfSql+=  ( "  and  a.mk_district_code  in " +subProvDistCodesStr) ;
          	                                                                                                     lastPartOfSql+=  ( " and  town not in ( select eng_description from ldd "+
          	                                                                                                                               " where code not in " +subProvDistCodesStr+")") ;

          	                                                                                            }else{
          	                                                                                                	         lastPartOfSql+=  ("    and a.mk_prv_code = " + province); 
          	                                                                                    	                     lastPartOfSql+= getPostCodeEliminatorStr(province);
          	                                                                                                  }
          	                                                                                   }
                                                                	                          if (district!=null && district.compareTo(Short.parseShort("0"))>0){
                                                                	                        	                        lastPartOfSql+=  ( "  and c.code=" + district);
                                                                	                        	                        lastPartOfSql+=  ( " and  town not in ( select eng_description from ldd "+
	                                                                                                                               " where code not in (" + district+")") ;

                                                                	                         }
          	                                                                  }
          	                                              return  lastPartOfSql;
              }
              private String getSubProvDistCodes(short subProvCode, short provinceCode)throws Exception{
                                                        DistrictUI districtUI=new DistrictUI();
                                                          List list=districtUI.getDistrictList(provinceCode);
                                                        Iterator iter=list.iterator();
                                                        String subProvDistCodes="(";
                                                        while(iter.hasNext()){
                                                                District  district= (District)iter.next();
                                                                if(district.getSubProvCode()==subProvCode){
                            	                                           subProvDistCodes+=(" "+district.getCode());
                                                                           if(iter.hasNext()){
                                            	                                    subProvDistCodes+=("   ,  ");
                                                                          }
                                                                }
                                                        }
                                                        if(!subProvDistCodes.isEmpty()){
                                                                 subProvDistCodes= subProvDistCodes.substring(0, subProvDistCodes.lastIndexOf(','));
                                                        }
                                                        subProvDistCodes+=")  ";
                                            return  subProvDistCodes;
          }
          public List getTownList(String country,Short province,Short district) throws Exception { 
            	                                                                   String sql= getSqlForSelectingnTowns(country,province,district);
            	                                                                   List listTown  = new ArrayList<Town>();
            	     		                                                      String errorMsg="Error reading frm the  tpusch  table;TownDAO";
            	     		                                                      List queryList = dbUtil.queryForList(sql,errorMsg);
            	     		                                                      for (int i=0; i<queryList.size();i++){
            	     			                                                                         Town town= new  Town ();	
            	     			                                                                          ListOrderedMap data = (ListOrderedMap) queryList.get(i);
            	     			                                                                          town.setCode( dbUtil.replaceNull(data.get("nameOfTown")));
            	     			                                                                          town.setName(dbUtil.replaceNull(data.get("nameOfTown")));
            	     			                                                                          listTown.add(town);						
            	     			                                                   }
            	     		                                                      return listTown; 
          }
}
