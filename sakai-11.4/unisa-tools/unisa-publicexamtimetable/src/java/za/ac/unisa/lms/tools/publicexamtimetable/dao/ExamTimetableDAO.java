package za.ac.unisa.lms.tools.publicexamtimetable.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;

public class ExamTimetableDAO extends StudentSystemDAO{

	public ExamTimetableDAO(){

	}

	public String getExamStatus(Integer year, Integer period) throws Exception{
		String examStatus ="";
		String sql = "select ADMISSION_DONE_FLAG from xaddon where mk_exam_year = "+year+" and mk_exam_period = "+period+" and mk_exam_type_code = 1";
		try{
			examStatus = this.querySingleValue(sql,"ADMISSION_DONE_FLAG");
		} catch (Exception ex) {
            throw new Exception("ExamTimeTableDAO error occurs / "+ ex,ex);
		}
       return examStatus;
	}

	public boolean isCodeInValid(String subject) throws Exception{
		String code ="";
		String sql = "select CODE from sun where CODE like '"+subject+"%'";
		try{
			code = this.querySingleValue(sql,"CODE");
		} catch (Exception ex) {
            throw new Exception("ExamTimeTableDAO method (isCodeInValid) error occurs / "+ ex,ex);
		}
		if (code.equalsIgnoreCase("")){
			return true;
		} else {

			return false;
		}
	}

	public ArrayList xxgetExamTimetableList(String year,String period,String subjCodes) throws Exception{
		ArrayList results = new ArrayList();
		String sql;
		sql =	"select SUBSTR(XAMDAT.FK_STUDY_UNIT_CODE,1,7) STUDY_CODE," +
		 		"XAMDAT.FK_NR PAPER," +
		 		"TO_CHAR(XAMDAT.DATE0,'YYYY-MM-DD') EXAM_DATE," +
		 		"TO_CHAR(XAMDAT.STARTING_TIME,'HH24:MI') START_TIME," +
		 		"NVL(XAMDAT.DURATION_HOURS,'0') HOURS ," +
		 		" NVL(XAMPAP.DURATION_MINUTES,'0') MINUTES " +
		 		"from XAMDAT, XAMPAP " +
		 		"where XAMDAT.MK_EXAM_PERIOD_COD =  " +period+
		 		" AND XAMDAT.FK_EXAM_YEAR =  "+year+
		 		" AND XAMPAP.EXAM_YEAR =  "+year+
		 		" AND XAMPAP.MK_STUDY_UNIT_CODE = XAMDAT.FK_STUDY_UNIT_CODE "+
		 		"AND XAMPAP.NR = XAMDAT.FK_NR ";
				String[] result = subjCodes.split(",");
				for(int i=0 ; i < result.length; i++){
					if (i == 0){
						sql +=	"AND (upper(XAMDAT.FK_STUDY_UNIT_CODE) LIKE '"+result[i].toUpperCase()+"%'";
					} else {
						sql += " OR upper(XAMDAT.FK_STUDY_UNIT_CODE) LIKE '"+result[i].toUpperCase()+"%' ";
					}
				}
		sql +=	") ORDER by FK_STUDY_UNIT_CODE, FK_NR ASC";
		try{
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			Iterator i = queryList.iterator();
			String examYear = "";
			while(i.hasNext()){
				ListOrderedMap data = (ListOrderedMap) i.next();
				ExamTimetableDetails examTimetableDetails = new ExamTimetableDetails();
				examTimetableDetails.setStudyUnit(data.get("STUDY_CODE").toString());
				examTimetableDetails.setPaperNo(data.get("PAPER").toString());
				examYear = data.get("EXAM_DATE").toString().substring(0,4);
				if (examYear.equalsIgnoreCase("1903")){
					examTimetableDetails.setExamDate("Departmental Requirements");
				} else if (examYear.equalsIgnoreCase("1901")){
					examTimetableDetails.setExamDate("Will be informed");
				} else {
					examTimetableDetails.setExamDate(data.get("EXAM_DATE").toString());
				}
				examTimetableDetails.setExamTime(data.get("START_TIME").toString());
				
				String minutes=data.get("MINUTES").toString();
				if(minutes.equals("0")){
					examTimetableDetails.setDuration(data.get("HOURS").toString()+" hour(s)");
					
				}else{
					examTimetableDetails.setDuration(data.get("HOURS").toString()+" hour(s)"+" "+minutes+" minutes");
					
				}
				
								
				results.add(examTimetableDetails);
			}
		}catch(Exception ex) {
			throw new Exception("ExamTimetableDAO : error reading the getExamTimetableList function / "+ ex,ex);
		}
		return results;
	}
	
	public ArrayList getExamTimetableList(String year,String period,String subjCodes) throws Exception{
		ArrayList results = new ArrayList();
		String sql;
		sql =	"select t.module as module,t.fk_nr as paper,decode(t.exam_date,'19010101','Will be informed','19030303'," + 
				" decode(t.paper_type_gc22,'EXISTNVB',t.due_date," + 
				"'CONASSESS',t.due_date," +
				"'PFMYADM',t.open_submission || ' - ' || t.final_submission," + 
				"'MCQSAMIGO',t.open_submission || ' - ' || t.final_submission," + 
				"'MCQMYEXAMS',t.open_submission || ' - ' || t.final_submission," + 
				"'HOMEMYADM',t.open_submission || ' - ' || t.final_submission," + 
				"'HOMEMYEXM',t.open_submission || ' - ' || t.final_submission," + 
				"'FMCQMYADM',t.open_submission || ' - ' || t.final_submission," + 
				"'ORAL',t.open_submission || ' - ' || t.final_submission," + 
				"'Module Arrangement'),t.venue_exam_date || ' ' || t.start_time || ' - ' || t.end_time) as exam_date," + 
				" t.exam_type as exam_type" + 
				" from" + 
				" (select XAMDAT.FK_STUDY_UNIT_CODE as Module,XAMDAT.FK_NR,to_char(XAMDAT.DATE0,'YYYYMMDD') as exam_date,to_char(XAMDAT.DATE0,'dd Mon YYYY') as venue_exam_date," + 
				" to_char(XAMDAT.STARTING_TIME,'HH24:MI') as start_time," + 
				" to_char(xamdat.starting_time + to_dsinterval('PT' || trim(to_char(XAMDAT.DURATION_HOURS,'09')) || 'H' || trim(to_char(XAMDAT.DURATION_MINUTES,'09')) || 'M'),'HH24:MI') as end_time," + 
				" XAMDAT.DURATION_HOURS,XAMDAT.PAPER_TYPE_GC22,XAMDAT.MK_UNIQUE_NR," + 
				" decode(to_char(XAMDAT.DATE0,'YYYYMMDD'),'19010101',' ','19030303',(select GENCOD.ENG_DESCRIPTION from gencod where gencod.code='NONVNB' and fk_gencatcode=22)," +
				" (select GENCOD.ENG_DESCRIPTION from gencod where gencod.code='VENUEXAM' and fk_gencatcode=22)) as exam_type," +
				" UNQASS.ASSESS_GROUP_GC230 as ass_type," + 
				" to_char(unqass.closing_date,'dd Mon YYYY') as due_date," + 
				" to_char(UNQASS.FINAL_SUBMIT_DATE,'dd Mon YYYY HH24:MI') as final_submission," + 
				" to_char(UNQASS.PF_OPEN_DATE,'dd Mon YYYY') || ' ' || to_char(UNQASS.PF_OPEN_TIME,'HH24:MI') as open_submission" + 
				" from xamdat,unqass" + 
				" where" + 
				" XAMDAT.MK_EXAM_PERIOD_COD="+period+
				" and XAMDAT.FK_EXAM_YEAR="+year+
				" and XAMDAT.FK_STUDY_UNIT_CODE in"; 				
				String[] result = subjCodes.split(",");
				for(int i=0 ; i < result.length; i++){
					if (i == 0){
						sql +=	" ('" +result[i].toUpperCase()+"'";
					} else {
						sql += ",'" +result[i].toUpperCase()+"'";
					}
				}
		sql +=	")" +				
				" and UNQASS.UNIQUE_NR(+)=XAMDAT.MK_UNIQUE_NR" + 
				" and UNQASS.ASSESS_GROUP_GC230(+)='S') t" +
				" order by t.Module, t.fk_nr ASC";
		try{
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			Iterator i = queryList.iterator();
			String examYear = "";
			while(i.hasNext()){
				ListOrderedMap data = (ListOrderedMap) i.next();
				ExamTimetableDetails examTimetableDetails = new ExamTimetableDetails();
				examTimetableDetails.setStudyUnit(data.get("module").toString());
				examTimetableDetails.setPaperNo(data.get("paper").toString());
				examTimetableDetails.setExamDate(data.get("exam_date").toString());
				examTimetableDetails.setExamType(data.get("exam_type").toString());		
								
				results.add(examTimetableDetails);
			}
		}catch(Exception ex) {
			throw new Exception("ExamTimetableDAO : error reading the getExamTimetableList function / "+ ex,ex);
		}
		return results;
	}

	private String querySingleValue(String query, String field){
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
    	List queryList;
    	String result = "";

 	   queryList = jdt.queryForList(query);
 	   Iterator i = queryList.iterator();
 	   i = queryList.iterator();
 	   if (i.hasNext()) {
			 ListOrderedMap data = (ListOrderedMap) i.next();
			 if (data.get(field) == null){
			 } else {
				 result = data.get(field).toString();
			 }
 	   }

 	   return result;
	}
}
