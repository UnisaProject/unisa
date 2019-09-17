package za.ac.unisa.lms.tools.tpustudentplacement.dao;

import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections.map.ListOrderedMap;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.PracticeBatchDate;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.PlacementUtilities;
public class PracticePeriodDAO {
	
	 databaseUtils dbutil;
	 public PracticePeriodDAO(){
		                         dbutil=new databaseUtils ();
	 }
	 
	  protected void  saveListOfPracticePeriods(List <PracticeBatchDate> pracPeriodList)throws Exception{
 		                         for(PracticeBatchDate practicePeriod:pracPeriodList){
 			                              savePracticePeriod(practicePeriod);
 		                         }
     }
    public void savePracticePeriod(PracticeBatchDate  practicePeriod)throws Exception{
                                                       String sql="insert into tpupracprd(mk_aacademic_year,Start_Date,End_Date,Fk_prv_code,Study_level,Practical_period,Practical_days)values("+
                                                                        practicePeriod.getAcademicYear()+", to_date('YYYY/MM/DD','"+ 
                                                    		            practicePeriod.getFromDate()+"),to_date('YYYY/MM/DD','"+practicePeriod.getToDate()+
                                                                        "'),"+practicePeriod.getProvCode()+","+ practicePeriod.getLevel()+","+
                                                                        practicePeriod.getPracticalPeriod()+","+practicePeriod.getPracticalDays()+")";
                                                        String erorrMsg="error inserting into tpupracperiod  in PracticePeriodDAO";
                                                        dbutil.update(sql,erorrMsg);
      }
    public void updatePracticePeriod(PracticeBatchDate  practicePeriod)throws Exception{
                                                 String sql="update  tpupracprd  set "+
                                                                 " Start_Date=to_date('YYYY/MM/DD','"+ practicePeriod.getFromDate()+"') ,End_Date=to_date('YYYY/MM/DD','"+practicePeriod.getToDate()+
                                                                "'),Fk_prv_code="+practicePeriod.getProvCode()+",Study_level="+ practicePeriod.getLevel()+",Practical_period="+
                                                                 practicePeriod.getPracticalPeriod()+",Practical_days="+practicePeriod.getPracticalDays();
                                                  String erorrMsg="error updating  tpupracperiod  in PracticePeriodDAO";
                                                 dbutil.update(sql,erorrMsg);
  }
  public List getPracticePeriodList( PracticeBatchDate practicePeriod)throws Exception{
	                                      List  practicePeriodList=new java.util.ArrayList();
	                                      String sql="select  Mk_academic_year ,Study_level,Practical_period, Fk_prv_code,Start_Date,End_Date,Practical_days "+
	                                                         " eng_description  from tpupracprd a,prov b where   a.Fk_prv_code=b.code ";
                                           if(practicePeriod.getProvCode()!=-1){
	        	                                                          sql+=(" and    Fk_prv_code="+practicePeriod.getProvCode());
	                                       }
                                          if(practicePeriod.getLevel()!=-1){
	                                                                                      sql+=(" and Study_level="+practicePeriod.getLevel());
                                          }
	                                      if(practicePeriod.getPracticalPeriod()!=-1){
			                                                              sql+=(" and practical_period="+practicePeriod.getPracticalPeriod());
	                                       }
                                          if((practicePeriod.getFromDate()!=null)&&
			                                      (practicePeriod.getFromDate().trim().isEmpty())){
		                                                          sql+=(" and Start_Date=" +practicePeriod.getFromDate());
	                                       }
                                           if((practicePeriod.getToDate()!=null)&&
			                                        (!practicePeriod.getToDate().trim().isEmpty())){
		                                                      sql+=(" and  End_Date=" +practicePeriod.getToDate());
	                                     }
                                           if(PlacementUtilities.isInt(""+practicePeriod.getAcademicYear())&&
                                        		      (practicePeriod.getAcademicYear()>2000)&& (practicePeriod.getAcademicYear()>2040)){
		                                                      sql+=(" and Mk_academic_year=" +practicePeriod.getAcademicYear());
		                                  }
                                          String errorMsg="error reading tpupracperiod  in PracticePeriodDAO";
                                          List queryList=dbutil.queryForList(sql, errorMsg);
                                          Iterator i = queryList.iterator();
                                           while (i.hasNext()) {
                                                                 ListOrderedMap data = (ListOrderedMap) i.next();
                                                                 PracticeBatchDate   practiceBatchDate=new  PracticeBatchDate();
                                                                 practiceBatchDate.setAcademicYear(Integer.parseInt(dbutil.replaceNull(data.get("mk_academic_year"))));
                                                                 practiceBatchDate.setFromDate(dbutil.replaceNull(data.get("from_date")));
                                                                 practiceBatchDate.setToDate(dbutil.replaceNull(data.get("to_date")));
                                                                 practiceBatchDate.setProvCode(Integer.parseInt(dbutil.replaceNull(data.get(" Fk_prv_code"))));
                                                                 practiceBatchDate.setProvDescr(dbutil.replaceNull(data.get("eng_description")));
                                                                 practiceBatchDate.setPracticalDays(Integer.parseInt(dbutil.replaceNull(data.get("practical_days"))));
                                                                 practiceBatchDate.setLevel(Integer.parseInt(dbutil.replaceNull(data.get("study_level"))));
                                                                 practicePeriodList.add( practiceBatchDate);
                                          }
                                        return practicePeriodList;
   }
    public void deletePracticalDateBatch(PracticeBatchDate practicePeriod)throws Exception{
	                                                String sql="delete  from   tpupracprd  where  "+
                                                                      " Start_Date=to_date('YYYY/MM/DD','"+ practicePeriod.getFromDate()+"')   and End_Date=to_date('YYYY/MM/DD','"+
	                                                		           practicePeriod.getToDate()+ "'),  and Fk_prv_code="+practicePeriod.getProvCode()+" and Study_level="+ practicePeriod.getLevel()+
	                                                		           "  and Practical_period="+practicePeriod.getPracticalPeriod()+" and mk_academic_year="+practicePeriod.getAcademicYear();
                                                      String erorrMsg="error updating  tpupracperiod  in PracticePeriodDAO";
                                                    dbutil.update(sql,erorrMsg);
	  
  }
}
