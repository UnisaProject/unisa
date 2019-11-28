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
    	                                               if(batchesHasSameKey(practicePeriod)){
    	                                            	   deletePracticalDateBatch(practicePeriod);
    	                                               }
                                                       String sql="insert into tpupracprd(mk_academic_year,Start_Date,End_Date,Fk_prv_code,Study_level,Practical_period,Practical_days)values("+
                                                                        practicePeriod.getAcademicYear()+", to_date('"+ 
                                                    		            practicePeriod.getStartDate()+"','YYYY/MM/DD'),to_date('"+practicePeriod.getEndDate()+
                                                                        "','YYYY/MM/DD'),"+practicePeriod.getProvCode()+","+ practicePeriod.getLevel()+","+
                                                                        practicePeriod.getPracticalPeriod()+","+practicePeriod.getPracticalDays()+")";
                                                        String erorrMsg="error inserting into tpupracperiod  in PracticePeriodDAO";
                                                        dbutil.update(sql,erorrMsg);
      }
    public void updatePracticePeriod(PracticeBatchDate  practicePeriod,PracticeBatchDate originalPracticeBatchDate)throws Exception{
    	                                          deletePracticalDateBatch(originalPracticeBatchDate);
    	                                           savePracticePeriod(practicePeriod);
   }
   public List getPracticePeriodList( PracticeBatchDate practicePeriod)throws Exception{
	                                      List  practicePeriodList=new java.util.ArrayList();
	                                      String sql="select  mk_academic_year ,Study_level,Practical_period, Fk_prv_code,Start_Date,End_Date,Practical_days "+
	                                                         ", eng_description  from tpupracprd a,prv b where   a.Fk_prv_code=b.code ";
                                           if(practicePeriod.getProvCode()!=-1){
	        	                                                          sql+=(" and    a.Fk_prv_code="+practicePeriod.getProvCode());
	                                       }
                                          if(practicePeriod.getLevel()!=-1){
	                                                                                      sql+=(" and Study_level="+practicePeriod.getLevel());
                                          }
	                                      if(practicePeriod.getPracticalPeriod()!=-1){
			                                                              sql+=(" and practical_period="+practicePeriod.getPracticalPeriod());
	                                       }
                                           if(PlacementUtilities.isInt(""+practicePeriod.getAcademicYear())&&
                                        		      (practicePeriod.getAcademicYear()!=-1)){
		                                                      sql+=(" and Mk_academic_year=" +practicePeriod.getAcademicYear());
		                                    }
                                             sql+=(" order by mk_academic_year,eng_description,study_level,practical_period,Start_Date,End_Date");
                                          String errorMsg="error reading tpupracprd  in PracticePeriodDAO";
                                          List queryList=dbutil.queryForList(sql, errorMsg);
                                          Iterator i = queryList.iterator();
                                          int x=1;
                                           while (i.hasNext()) {
                                                                 ListOrderedMap data = (ListOrderedMap) i.next();
                                                                 PracticeBatchDate   practiceBatchDate=new  PracticeBatchDate();
                                                                 practiceBatchDate.setAcademicYear(Integer.parseInt(dbutil.replaceNull(data.get("mk_academic_year"))));
                                                                 String startDateStr=dbutil.replaceNull(data.get("Start_Date"));
                                                                 if(startDateStr.length()>10){
                                                                	           startDateStr=startDateStr.substring(0,10);
                                                                 }
                                                                 practiceBatchDate.seStartDate(startDateStr);
                                                                 String endDateStr=dbutil.replaceNull(data.get("End_Date"));
                                                                 if(endDateStr.length()>10){
                                                                	      endDateStr=endDateStr.substring(0,10);
                                                                 }
                                                                 practiceBatchDate.setEndDate(endDateStr);
                                                                 practiceBatchDate.setProvCode(Integer.parseInt(dbutil.replaceNull(data.get("Fk_prv_code"))));
                                                                 practiceBatchDate.setProvDescr(dbutil.replaceNull(data.get("eng_description")));
                                                                 practiceBatchDate.setPracticalDays(Integer.parseInt(dbutil.replaceNull(data.get("Practical_days"))));
                                                                 practiceBatchDate.setLevel(Integer.parseInt(dbutil.replaceNull(data.get("study_level"))));
                                                                 practiceBatchDate.setPracticalPeriod(Integer.parseInt(dbutil.replaceNull(data.get("practical_period"))));
                                                                 practiceBatchDate.setIndex(x++);
                                                                 practiceBatchDate.setPracticalDateRange( (practiceBatchDate.getStartDate()+"   -   "+practiceBatchDate.getEndDate()+ " ("+practiceBatchDate.getPracticalDays()+")"));
                                                                 practicePeriodList.add(practiceBatchDate);
                                          }
                                        return practicePeriodList;
   }
  public boolean  batchesHasSameKey( PracticeBatchDate practicePeriod)throws Exception{
                                     String sql="select  mk_academic_year ,Study_level,Practical_period, Fk_prv_code,Start_Date,End_Date,Practical_days "+
                                                       ", eng_description  from tpupracprd a,prv b where   a.Fk_prv_code=b.code ";
                                                 if(practicePeriod.getProvCode()!=-1){
                                      sql+=(" and    a.Fk_prv_code="+practicePeriod.getProvCode());
       }
      if(practicePeriod.getLevel()!=-1){
                                                      sql+=(" and Study_level="+practicePeriod.getLevel());
      }
      if(practicePeriod.getPracticalPeriod()!=-1){
                                      sql+=(" and practical_period="+practicePeriod.getPracticalPeriod());
       }
         if(PlacementUtilities.isInt(""+practicePeriod.getAcademicYear())&&
    		      (practicePeriod.getAcademicYear()!=-1)){
                          sql+=(" and Mk_academic_year=" +practicePeriod.getAcademicYear());
      }
         sql+=(" and to_char(start_date,'YYYY/MM/DD')='" +practicePeriod.getStartDate()+"'");
         sql+=(" order by mk_academic_year,eng_description,study_level,practical_period,Start_Date");
      String errorMsg="error reading tpupracprd  in PracticePeriodDAO";
      List queryList=dbutil.queryForList(sql, errorMsg);
      boolean haSameStartDate=false;
       if((queryList!=null)&&queryList.size()>0){
     			 		    	           haSameStartDate=true;
        }
		 return haSameStartDate;
}   
  public void deletePracticalDateBatch(PracticeBatchDate practicePeriod)throws Exception{
	                                                String sql="delete  from   tpupracprd  where  "+
                                                                      " Start_Date=to_date('"+practicePeriod.getStartDate()+ "','YYYY/MM/DD')  and Fk_prv_code="+practicePeriod.getProvCode()+" and Study_level="+ practicePeriod.getLevel()+
	                                                		           "  and Practical_period="+practicePeriod.getPracticalPeriod()+" and mk_academic_year="+practicePeriod.getAcademicYear();
                                                      String erorrMsg="error updating  tpupracperiod  in PracticePeriodDAO";
                                                    dbutil.update(sql,erorrMsg);
	  }
    public void deleteAllForAyearExceptProv(int year,int provCode)throws Exception{
                                          String sql="delete  from   tpupracprd  where  "+
                                                           " mk_academic_year="+ year+"  and fk_prv_code<>"+provCode;
                                                               String erorrMsg="error deleting   tpupracperiod  in PracticePeriodDAO";
                                                            dbutil.update(sql,erorrMsg);
   }
}
