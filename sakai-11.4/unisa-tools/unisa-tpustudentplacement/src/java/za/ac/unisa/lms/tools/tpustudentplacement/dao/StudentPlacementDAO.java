package za.ac.unisa.lms.tools.tpustudentplacement.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;
import za.ac.unisa.lms.dao.Gencod;
import za.ac.unisa.lms.dao.StudentSystemGeneralDAO;
import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.dao.ContactDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.dao.databaseUtils;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.schoolImpl.SchoolUI;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.PlacementListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Province;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Contact;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Student;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.School;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacement;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.DateUtil;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.PlacementUtilities;
public class StudentPlacementDAO extends StudentSystemDAO {
	private final String mentordatasql="(select (d.surname || ' ' || d.initials || ' ' || d.mk_title)"+ 
	        " from  tpumen d where (d.code is not null)  and a.mk_mentor_code=d.code ) as mentorName,(select code "+
	        " from  tpumen d where (d.code is not null)  and a.mk_mentor_code=d.code )as mentorCode ";	
            
	public List getSemesterList(){
		            List list = new ArrayList();
		            StudentSystemGeneralDAO dao = new StudentSystemGeneralDAO();
		            for (int i=0; i < dao.getGenCodes((short)54,1).size(); i++) {
			                list.add(i, (Gencod)(dao.getGenCodes((short)54,1).get(i)));
		            }
		          return list;
		          
	}
		public void insertStudentPlacement(Short acadYear, Short semester, Integer studentNr, StudentPlacement placement) throws Exception {
			
		String sql = "insert into tpuspl" +
		    " (mk_academic_year,semester_period,mk_student_nr,mk_school_code,mk_study_unit_code," +
		    " mk_supervisor_code,start_date,end_date,number_of_weeks,evaluation_mark,email_to_sup,STU_FULLTIME_SCH,"+
		    "MK_MENTOR_CODE,practice_period) " +
			"values " +
			"(" + acadYear + "," +
			semester + "," +
			studentNr + "," +
			placement.getSchoolCode() + "," +
			"'" + placement.getModule() + "'," +
			placement.getSupervisorCode() + "," +
			"to_date('" + placement.getStartDate().toUpperCase().trim()+ "','YYYY/MM/DD')," +
			"to_date('" + placement.getEndDate().toUpperCase().trim()+ "','YYYY/MM/DD')," +
			Short.parseShort(placement.getNumberOfWeeks().trim())+",";
		if (placement.getEvaluationMark().trim().equalsIgnoreCase("")){
			sql = sql  + " null";
		}else{
			sql = sql+ Short.parseShort(placement.getEvaluationMark().trim()) ;
		}
		sql+=(",null,'"+placement.getStuFullTime()+"'");
		if((placement.getMentorCode()==null)||placement.getMentorCode()==0){
			sql+=(",null");
		}else{
			sql+=(","+placement.getMentorCode());
		}
		sql+=(","+placement.getPlacementPrd()+")");
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			int result = jdt.update(sql);	
		}
		
		catch (Exception ex) {
			throw new Exception("StudentPlacementDao : Error inserting TPUSPL / " + ex,ex);
		}	
	}
	
	public void updateStudentPlacement(Short acadYear, Short semester, Integer studentNr, String originalModule, Integer originalSchool, StudentPlacement placement,int originalSupCode) throws Exception {
		
		String sql = "update tpuspl" +
		" set mk_school_code=" + placement.getSchoolCode() + "," +
		" mk_study_unit_code='" + placement.getModule() + "'," +
		" mk_supervisor_code=" + placement.getSupervisorCode() + "," +
		" start_date=to_date('" + placement.getStartDate().toUpperCase().trim()+ "','YYYY/MM/DD')," +
		" end_date=to_date('" + placement.getEndDate().toUpperCase().trim()+ "','YYYY/MM/DD')," +
		" number_of_weeks=" + Short.parseShort(placement.getNumberOfWeeks().trim()) + ",";
		  	if (placement.getEvaluationMark()==null || placement.getEvaluationMark().trim().equalsIgnoreCase("")){
			sql = sql  + "evaluation_mark=null";
		}else{
			sql = sql  + "evaluation_mark=" + Short.parseShort(placement.getEvaluationMark().trim());
		}
		sql+=(",stu_fulltime_sch='"+placement.getStuFullTime()+"'");
		if((placement.getMentorCode()==null)||placement.getMentorCode()==0){
			sql+=(",mk_mentor_code=null");
		}else{
			sql+=(",mk_mentor_code="+placement.getMentorCode());
		}
		
		if(originalSupCode!=placement.getSupervisorCode()){
			sql+=" ,email_to_sup=null ";
	     }
			sql = sql +	" where mk_academic_year=" + acadYear +
		" and semester_period=" + semester +
		" and mk_student_nr=" + studentNr +
		" and mk_study_unit_code='" + originalModule + "'" +
		" and mk_school_code=" + originalSchool+
			" and practice_period=" + placement.getPlacementPrd();
		try{ 
			   JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			   int result = jdt.update(sql);	
		}
		catch (Exception ex) {
			throw new Exception("StudentPlacementDao : Error updating TPUDIS / " + ex,ex);
		}	
	}
public List getStudentPlacementList(Short acadYear, Short semester, Integer studentNr) throws Exception {
	                                  List placementList = new ArrayList<StudentPlacementListRecord>();
		                                                               String sql = " select a.mk_school_code as schCode, b.name as schName,b.town as town, a.mk_study_unit_code as module," + 
		                                                              "  a.mk_supervisor_code as supCode, (c.surname || ' ' || c.initials || ' ' || c.mk_title) as supName," +
		                                                              "  to_char(a.start_date,'YYYY/MM/DD') as startDate,to_char(a.end_date,'YYYY/MM/DD') as endDate," +
		                                                               "  a.number_of_weeks as numWeeks,"+
		                                                               "  to_char(a.start_date_sec_prd,'YYYY/MM/DD') as startDate_sec_prd,to_char(a.end_date_sec_prd,'YYYY/MM/DD') as endDate_sec_prd," +
		                                                               "  a.number_of_weeks_sec_prd as numWeeks_sec_prd,"+
		                                                               "  a.evaluation_mark as evalMark,a.practice_period as practiceprd,"+mentordatasql+",stu_FullTime_sch " +
		                                                               "  from tpuspl a, tpusch b, tpusup c" +
		                                                               "  where a.mk_academic_year=" + acadYear +
		                                                               " and a.semester_period=" + semester +
		                                                               " and a.mk_student_nr=" + studentNr +
		                                                              "  and a.mk_school_code=b.code" + 
		                                                                "  and a.mk_supervisor_code=c.code" +
		                                                               " and a.mk_school_code=b.code" + 
		                                                               " order by a.mk_study_unit_code";
				
		             databaseUtils dbutil=new databaseUtils();
			         String errorMessage="StudentPlacementDao : Error getting placementList, reading TPUSPL / ";
			         List queryList = dbutil.queryForList(sql,errorMessage);
			         Iterator i = queryList.iterator();
			         
			         while (i.hasNext()) {
				           ListOrderedMap data = (ListOrderedMap) i.next();
				           StudentPlacementListRecord placement = new StudentPlacementListRecord();
				           placement.setSchoolCode(Integer.parseInt(data.get("schCode").toString()));
				           placement.setSchoolDesc(data.get("schName").toString());
				           placement.setSupervisorCode(Integer.parseInt(data.get("supCode").toString()));
				           placement.setSupervisorName(data.get("supName").toString());
				           placement.setModule(data.get("module").toString());
				           placement.setStartDate(data.get("startDate").toString());
				           placement.setEndDate(data.get("endDate").toString());
				           placement.setNumberOfWeeks(dbutil.replaceNull(data.get("numWeeks")));	
				           placement.setEvaluationMark(dbutil.replaceNull(data.get("evalMark")));	
				           placement.setTown(dbutil.replaceNull(data.get("town")));
				           placement.setStuFullTime(dbutil.replaceNull(data.get("stu_FullTime_sch")));
				           String mentorCode=dbutil.replaceNull(data.get("mentorCode"));
					       if( mentorCode.trim().equals("")){
				        	   mentorCode="0";
				           }
				           placement.setMentorCode(Integer.parseInt(mentorCode));
				           placement.setMentorName(dbutil.replaceNull(data.get("mentorName")));
				           Contact contact = new Contact();
				           String supervisorContactNr=getSupervisorContactNumber(placement.getSupervisorCode(),new ContactDAO(),contact);
				           placement.setSupervisorContactNumber(supervisorContactNr);
				           String practiceprdStr=  dbutil.replaceNull(data.get("practiceprd"));
			                 if(practiceprdStr.trim().equals(""))
			                	 practiceprdStr="0";
				                 placement.setPlacementPrd(Integer.parseInt(practiceprdStr));
			                 placementList.add(placement);
			         }
	             return placementList;		
	}

public boolean isDateBlockAssigned(String fromDate,String toDate) throws Exception {
	                              DateUtil dateUtil=new DateUtil();
                                  List placementList = new ArrayList<StudentPlacementListRecord>();
                                  String sql = " select *  from tpuspl  " +
                                     "  where mk_academic_year= " + dateUtil.yearInt() +
                                     "  and semester_period=0  "+
                                     "  and mk_supervisor_code<>283 " +
                                     "  and to_char(start_date,'YYYY-MM-DD')='"+fromDate+"'"+
                                     "  and to_char(end_date,'YYYY-MM-DD')='"+toDate+"'";
                                      databaseUtils dbutil=new databaseUtils();
                                 String errorMessage="StudentPlacementDao : Error getting checking if datea block assigned to placement, reading TPUSPL / ";
                                 List queryList = dbutil.queryForList(sql,errorMessage);
                                 if((queryList==null)||(queryList.isEmpty())){
                                	           return false;
                                 }else{
                                	           return true;
                                 }
 }
    public StudentPlacement getStudentPlacement(Short acadYear,Short semester,Integer studentNr,String module, Integer school,Integer practicePrd) throws Exception {
	                                                              	StudentPlacement placement = new StudentPlacement();
		                                                        	String sql = " select a.mk_school_code as schCode, b.name as schName,b.town as town, a.mk_study_unit_code as module," + 
		                                                                                "  a.mk_supervisor_code as supCode, (c.surname || ' ' || c.initials || ' ' || c.mk_title) as supName," +
		                                                                                "  to_char(a.start_date,'YYYY/MM/DD') as startDate,to_char(a.end_date,'YYYY/MM/DD') as endDate,stu_fulltime_sch," +
		                                                                               " a.number_of_weeks as numWeeks,a.evaluation_mark as evalMark, a.practice_period as practice_period,"+mentordatasql+
		                                                                                "  from tpuspl a, tpusch b, tpusup c" +
		                                                                                "  where a.mk_academic_year=" + acadYear +
	                                                                                	"  and a.semester_period=" + semester +
		                                                                                "  and a.mk_student_nr=" + studentNr +
	                                                                                 	"  and a.mk_study_unit_code='" + module + "'" +
		                                                                                "  and a.mk_school_code=" + school +
		                                                                                 "  and a.practice_period=" +practicePrd+
		                                                                              	"  and a.mk_school_code=b.code" + 
		                                                                                "  and a.mk_supervisor_code=c.code" +
	                                                                                	"  order by a.mk_study_unit_code";
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			databaseUtils dbutil=new databaseUtils();
		
			Iterator i = queryList.iterator();
			int x=0;
			while (i.hasNext()) {
				          ListOrderedMap data = (ListOrderedMap) i.next();
				          placement.setSchoolCode(Integer.parseInt(data.get("schCode").toString()));
				          placement.setSchoolDesc(data.get("schName").toString());
				          placement.setSupervisorCode(Integer.parseInt(data.get("supCode").toString()));
				          placement.setSupervisorName(data.get("supName").toString());
				          placement.setModule(data.get("module").toString());
				          if(x==0){
				                 placement.setStartDate(data.get("startDate").toString());
				                 placement.setEndDate(data.get("endDate").toString());
				                 placement.setNumberOfWeeks(dbutil.replaceNull(data.get("numWeeks")));	
				          }else{
				                       placement.setStartDateSecPracPeriod(dbutil.replaceNull(data.get("startDate")));
		                               placement.setEndDateSecPracPeriod(dbutil.replaceNull(data.get("endDate").toString()));
		                               placement.setNumberOfWeeks(dbutil.replaceNull(data.get("numWeeks")));	
				          }
		                  placement.setEvaluationMark(dbutil.replaceNull(data.get("evalMark")));	
				          placement.setTown(dbutil.replaceNull(data.get("town")));
				          placement.setStuFullTime(dbutil.replaceNull(data.get("stu_fulltime_sch")));
				         String mentorCode=dbutil.replaceNull(data.get("mentorCode"));
			              if( mentorCode.trim().equals("")){
		        	                mentorCode="0";
		                  }
		                  placement.setMentorCode(Integer.parseInt(mentorCode));
		                  placement.setMentorName(dbutil.replaceNull(data.get("mentorName")));
		                  placement.setPlacementPrd(Integer.parseInt(dbutil.replaceNull(data.get("practice_period"))));
		                  x++;
             }
		}
		catch (Exception ex) {
			throw new Exception("StudentPlacementDao : Error reading TPUSPL / " + ex,ex);
		}	
		return placement;
	}
	
	public void removeStudentPlacement(Short acadYear,Short semester,Integer studentNr,
			                              String module,int schoolCode,int pracprd) throws Exception {
		                                  String sql = "delete from tpuspl  where mk_academic_year=" + acadYear +
		                                                     " and semester_period=" + semester +
		                                                     " and mk_student_nr=" + studentNr +
		                                                      " and mk_study_unit_code='" + module + "'" +
		                                                       " and mk_school_code=" + schoolCode+
		                                                       "and  practice_period="+pracprd;
		                                   databaseUtils dbutil=new databaseUtils();
	                                       String errorMessage="StudentPlacementDao : Error deleting TPUSPL / ";
			                               dbutil.update(sql,errorMessage);	
 	 }
     private String getFirstPartOfSqlStrForPlacementList(Short acadYear, Short semester,String country){
    	          String  sqlStr="select a.mk_student_nr as stuNumber,(d.surname || ' ' || d.initials || ' ' || d.mk_title) as stuName,"+
	                    "  a.mk_school_code as schCode, b.name as schName,b.town as town,b.suburb as suburb, a.mk_study_unit_code as module,"+
	                    "  a.mk_supervisor_code as supCode, (c.surname || ' ' || c.initials || ' ' || c.mk_title) as supName,"+
	                    " to_char(a.start_date,'YYYY/MM/DD') as startDate,"+
	                    "  to_char(a.end_date,'YYYY/MM/DD') as endDate,stu_fulltime_sch,"+
	                    "  a.number_of_weeks as numWeeks,a.evaluation_mark as evalMark,a.mk_academic_year  as year, a.semester_period   as semester,"+
	                    " email_to_sup as dateSent,stu_FullTime_sch,practice_period as practiceprd";
               if((country!=null)&&country.equals(databaseUtils.saCode)){
            	   sqlStr+=" ,a.email_to_sup as dateSent ,e.code as disCode,e.eng_description as disName,"+mentordatasql+" from tpuspl a, tpusch b, tpusup c, stu d, ldd e";
               }else{
            	   sqlStr+=" from tpuspl a, tpusch b, tpusup c, stu d";
               }
               sqlStr+=" where a.mk_academic_year=" + acadYear +
                       " and a.semester_period=" + semester +
                       "  and a.mk_school_code=b.code" + 
                       "  and a.mk_supervisor_code=c.code" +
                       "  and a.mk_student_nr=d.nr";
               return sqlStr;
    }
     private String getFirstPartOfSqlStrForPrelimPlacementList(Short acadYear, Short semester,String country){
    	                                      String   secDateFrag= "( select   to_char(start_date,'YYYY/MM/DD')  || '-' ||  to_char(end_date,'YYYY/MM/DD')   || '-' ||  number_of_weeks  from tpuspl  "+
                                                                                 " where tpuspl.mk_student_nr =a.mk_student_nr and   tpuspl.mk_study_unit_code=a.mk_study_unit_code  "
                                                                                 + " and tpuspl.mk_academic_year=a.mk_academic_year "+
                                                                                 "  and tpuspl.mk_school_code= a.mk_school_code and tpuspl.semester_period=a.semester_period "+
                                                                                " and tpuspl.practice_period=2 )  as secDatesFragment";
	                                          String  sqlStr="select a.mk_student_nr as stuNumber,(d.surname || ' ' || d.initials || ' ' || d.mk_title) as stuName,"+
	                                                                 " a.mk_school_code as schCode, b.name as schName,b.town as town,b.suburb as suburb, a.mk_study_unit_code as module,"+
	                                                                 " a.mk_supervisor_code as supCode, (c.surname || ' ' || c.initials || ' ' || c.mk_title) as supName,"+
	                                                                 " to_char(a.start_date,'YYYY/MM/DD') as startDate,"+
	                                                                 " to_char(a.end_date,'YYYY/MM/DD') as endDate,stu_fulltime_sch,"+
	                                                                " a.number_of_weeks as numWeeks,a.evaluation_mark as evalMark,a.mk_academic_year  as year, a.semester_period   as semester,"+
	                                                                "email_to_sup as dateSent,stu_FullTime_sch,practice_period  as practiceprd,"+secDateFrag;
               if((country!=null)&&country.equals(databaseUtils.saCode)){
            	   sqlStr+=" ,a.email_to_sup as dateSent ,e.code as disCode,e.eng_description as disName,"+mentordatasql+" from tpuspl a, tpusch b, tpusup c, stu d, ldd e";
               }else{
            	   sqlStr+=" from tpuspl a, tpusch b, tpusup c, stu d";
               }
               sqlStr+=" where a.mk_academic_year=" + acadYear +
                       " and a.semester_period=" + semester +
                       "  and a.mk_school_code=b.code" + 
                       "  and a.mk_supervisor_code=c.code" +
                       "  and a.mk_student_nr=d.nr"+
                       " and a.practice_period=1";
               return sqlStr;
    }
    private String  getPlacementListSql(String   firstPartOfSqlStr,Short acadYear, Short semester, Short province, Short district, 
                      Integer supervisor, Integer school, String module, String sortOn,String country,String town){
	     
	                  String sql= firstPartOfSqlStr;//    
	                 if((country!=null)&&country.equals(PlacementUtilities.getSaCode())){
	                                    if((country!=null)&&country.equals(PlacementUtilities.getSaCode())){  
                                                         sql+=" and b.mk_district_code=e.code";
                                       }
	                                    if (district!=null && district.compareTo(Short.parseShort("0"))>0){
	                                        sql = sql.trim() + " and b.mk_district_code=" + district;
	                                  }
	                       if (province!=null && province.compareTo(Short.parseShort("0"))>0){
                            	                  if(Province.isProvince(province)){
                            	                      sql = sql.trim() + "   and b.mk_prv_code=" + province;
                            	                  }else{
                            	                         sql = sql.trim() + "  and  "+ province+" = ( select fk_tpusubprv_code  from ldd where  code=  b.mk_district_code   )" ;
                            	                  }
                              }
                                  
                     }
	                 if((town!=null)&&(!town.trim().isEmpty())&&(!town.trim().equals("-1"))){
                                sql+=" and b.town='"+town.trim()+"'";
                        }
                  if (school!=null && school!=0){
                            sql = sql.trim() + " and a.mk_school_code=" + school;
                     }
                     if (supervisor!=null && supervisor!=0){
                              sql = sql.trim() + " and a.mk_supervisor_code=" + supervisor;
                     }
                     if ((module!=null) && !module.trim().equalsIgnoreCase("") && !module.trim().equalsIgnoreCase("ALL")){
                              sql = sql.trim() + " and a.mk_study_unit_code='" + module + "'";
                     }
                     if ((country!=null) && !country.trim().equalsIgnoreCase("")){
                             sql = sql.trim() + " and b.mk_country_code=" +"'"+ country+"'";
                     }
                     if (sortOn.equalsIgnoreCase("District")){
                             if((country!=null)&&country.equals(PlacementUtilities.getSaCode())){
                                   sql= sql.trim() + " order by e.eng_description,d.surname || ' ' || d.initials || ' ' || d.mk_title,a.mk_study_unit_code";
                             }
                    }else if (sortOn.equalsIgnoreCase("Supervisor")){
                               sql= sql.trim() + " order by c.surname || ' ' || c.initials || ' ' || c.mk_title,d.surname || ' ' || d.initials || ' ' || d.mk_title,a.mk_study_unit_code";
                    }else if (sortOn.equalsIgnoreCase("Module")){
                              sql= sql.trim() + " order by a.mk_study_unit_code,d.surname || ' ' || d.initials || ' ' || d.mk_title";
                    }else if (sortOn.equalsIgnoreCase("School")){
                            sql= sql.trim() + " order by b.name,d.surname || ' ' || d.initials || ' ' || d.mk_title,a.mk_study_unit_code";
                    }else if (sortOn.equalsIgnoreCase("Town")){
                    	   sql= sql.trim() + " order by b.town,d.surname || ' ' || d.initials || ' ' || d.mk_title,a.mk_study_unit_code";
                    }else{
                           sql= sql.trim() + " order by c.surname || ' ' || c.initials || ' ' || c.mk_title,d.surname || ' ' || d.initials || ' ' || d.mk_title,a.mk_study_unit_code";
                    }
                    return sql;
     }
     public List getPrelimPlacementList(Short acadYear, Short semester, Short province, Short district, 
                                                      Integer supervisor, Integer school, String module, String sortOn,String country,String town) throws Exception {
    	                                                       String firstPartOfSsqlStr=getFirstPartOfSqlStrForPrelimPlacementList(acadYear,semester,country);   
                                                               String sql=getPlacementListSql(firstPartOfSsqlStr,acadYear,semester,province,district,supervisor,school,module,sortOn,country,town);
                                                              return setPrelimPlacementListFromDatabase(sql,country,province);		
     }
     public List getPlacementList(Short acadYear, Short semester, Short province, Short district, 
             Integer supervisor, Integer school, String module, String sortOn,String country,String town) throws Exception {
    	               String firstPartOfSqlStrForPlacementList=getFirstPartOfSqlStrForPlacementList(acadYear,semester,country);
                      String sql=getPlacementListSql(firstPartOfSqlStrForPlacementList,acadYear,semester,province,district,supervisor,school,module,sortOn,country,town);
                     return setPlacementListFromDatabase(sql,country,province);		
}
     public List getPlacementListForSupervEmail(int supervisorCode) throws Exception {
    	            DateUtil dateutil=new DateUtil();
    	            String firstPartOfSqlStrForPlacementList=getFirstPartOfSqlStrForPlacementList((short)dateutil.yearInt(),(short)0,null);
                    String sql=getPlacementListSql(firstPartOfSqlStrForPlacementList,(short)(dateutil.yearInt()),(short)0,null,null,supervisorCode,null,null,"Supervisor",null,null);
                    return setPlacementListFromDatabase(sql,null,null);	
     }
     private List setPlacementListFromDatabase(String sql,String country,Short province) throws Exception{
	            databaseUtils dbutil=new databaseUtils();
	            List placementList = new ArrayList<PlacementListRecord>();
	               	 List queryList = dbutil.queryForList(sql,"StudentPlacementDao : Error getting placementList, reading TPUSPL / ");
            		 Iterator i = queryList.iterator();
            		 ContactDAO contactDAO=new ContactDAO();
            		 Contact contact=new Contact();
            		 SchoolUI school=new SchoolUI();
       		         Student student=new  Student(); 
		             while (i.hasNext()) {
		                 ListOrderedMap data = (ListOrderedMap) i.next();
		                 PlacementListRecord placement = new PlacementListRecord();
		                 placement.setStudentName(data.get("stuName").toString());
		                 placement.setStudentNumber(Integer.parseInt(data.get("stuNumber").toString()));
		                 placement.setSchoolCode(Integer.parseInt(data.get("schCode").toString()));
		                 placement.setSchoolDesc(data.get("schName").toString());
		                 String supCodeStr=  dbutil.replaceNull(data.get("supCode"));
		                 if(supCodeStr.trim().equals(""))
		                	 supCodeStr="0";
		                 placement.setSupervisorCode(Integer.parseInt( supCodeStr));
		                 String practiceprdStr=  dbutil.replaceNull(data.get("practiceprd"));
		                 if(practiceprdStr.trim().equals(""))
		                	 practiceprdStr="1";
			                 placement.setPlacementPrd(Integer.parseInt(practiceprdStr));
		                 placement.setSupervisorName(data.get("supName").toString());
		                 placement.setModule(data.get("module").toString());
		                 placement.setStartDate(data.get("startDate").toString());
		                 placement.setEndDate(data.get("endDate").toString());
		                 placement.setNumberOfWeeks(dbutil.replaceNull(data.get("numWeeks")));	
		                    placement.setEvaluationMark(dbutil.replaceNull(data.get("evalMark")));	
		                 placement.setEmailSendDate(dbutil.replaceNull(data.get("dateSent")));
		                 if((country!=null)&&(country.equals(databaseUtils.saCode))){
		                	 String disCodeStr=  dbutil.replaceNull(data.get("disCode"));
		 	                   if(disCodeStr.trim().equals(""))
		 	                	  disCodeStr="0";
		 	                      placement.setDistrictCode(Short.parseShort(dbutil.replaceNull(data.get("disCode"))));
		                        placement.setDistrictDesc(data.get("disName").toString());
		                         placement.setProvinceCode(province);
		                 }
		                 String surburb=dbutil.replaceNull(data.get("suburb"));
		 				 placement.setTown(dbutil.replaceNull(data.get("town")));
		 				/* if(!surburb.trim().equals("")){
		 					     placement.setTown(surburb);
		 				 }*/
		 				 placement.setCountryCode(country);
		                 placement.setStudentContactNumber(student.getStudentContactNumber(placement.getStudentNumber()));
		                 placement.setSupervisorContactNumber(getSupervisorContactNumber(placement.getSupervisorCode(),contactDAO,contact));
		                 placement.setSchoolContactNumber(school.getSchoolContactNumber(placement.getSchoolCode()));
		                 String mentorCode=dbutil.replaceNull(data.get("mentorCode"));
					       if( mentorCode.trim().equals("")){
				        	   mentorCode="0";
				           }
				           placement.setMentorCode(Integer.parseInt(mentorCode));
				           placement.setStuFullTime(dbutil.replaceNull(data.get("stu_fulltime_sch")));
						   placement.setMentorName(dbutil.replaceNull(data.get("mentorName")));
		                   placementList.add(placement);
		           }
	            return placementList;
    }
     private List setPrelimPlacementListFromDatabase(String sql,String country,Short province) throws Exception{
         databaseUtils dbutil=new databaseUtils();
         List placementList = new ArrayList<PlacementListRecord>();
            	 List queryList = dbutil.queryForList(sql,"StudentPlacementDao : Error getting placementList, reading TPUSPL / ");
     		 Iterator i = queryList.iterator();
     		 ContactDAO contactDAO=new ContactDAO();
     		 Contact contact=new Contact();
     		 SchoolUI school=new SchoolUI();
		         Student student=new  Student(); 
	             while (i.hasNext()) {
	                 ListOrderedMap data = (ListOrderedMap) i.next();
	                 PlacementListRecord placement = new PlacementListRecord();
	                 placement.setStudentName(data.get("stuName").toString());
	                 placement.setStudentNumber(Integer.parseInt(data.get("stuNumber").toString()));
	                 placement.setSchoolCode(Integer.parseInt(data.get("schCode").toString()));
	                 placement.setSchoolDesc(data.get("schName").toString());
	                 String supCodeStr=  dbutil.replaceNull(data.get("supCode"));
	                 if(supCodeStr.trim().equals(""))
	                	 supCodeStr="0";
	                 placement.setSupervisorCode(Integer.parseInt( supCodeStr));
	                 String practiceprdStr=  dbutil.replaceNull(data.get("practiceprd"));
	                 if(practiceprdStr.trim().equals(""))
	                	 practiceprdStr="0";
		                 placement.setPlacementPrd(Integer.parseInt(practiceprdStr));
	                 placement.setSupervisorName(dbutil.replaceNull(data.get("supName")));
	                 placement.setModule(dbutil.replaceNull(data.get("module")));
	                 placement.setStartDate(dbutil.replaceNull(data.get("startDate")));
	                 placement.setEndDate(dbutil.replaceNull(data.get("endDate")));
	                 String secDatesFragment=  dbutil.replaceNull(data.get("secDatesFragment"));
	                 StringTokenizer stk=null;
	                 String secStartDateStr="";
	                 String secEndDateStr="";
	                 String secNumOfWeeksStr="";
	                 boolean twoPlacements=false;
	                   if((secDatesFragment!=null)&&(!secDatesFragment.trim().isEmpty())){
	                	                    stk=new  StringTokenizer(secDatesFragment,"-");
	                	                    if(stk.hasMoreTokens())
	                		                      secStartDateStr=stk.nextToken();
	                	                    if(stk.hasMoreTokens())
	                		                      secEndDateStr=stk.nextToken();
	                	                    if(stk.hasMoreTokens())
	                		                	 secNumOfWeeksStr=stk.nextToken();
	                		                 if(secNumOfWeeksStr.trim().equals("")){
	                		                	 secNumOfWeeksStr="";
	                		                 }
	                		                 twoPlacements=true;
	                  }
		             placement.setStartDateSecPracPeriod(secStartDateStr);
	                    placement.setEndDateSecPracPeriod(secEndDateStr);
	                  placement.setNumberOfWeeksSecPracPrd(secNumOfWeeksStr);
	                  placement.setTwoPlacements(twoPlacements);
	                    placement.setNumberOfWeeks(dbutil.replaceNull(data.get("numWeeks")));	
	                 placement.setEvaluationMark(dbutil.replaceNull(data.get("evalMark")));	
	                 placement.setEmailSendDate(dbutil.replaceNull(data.get("dateSent")));
	                 if((country!=null)&&(country.equals(databaseUtils.saCode))){
	                	  String disCodeStr=  dbutil.replaceNull(data.get("disCode"));
	 	                   if(disCodeStr.trim().equals(""))
	 	                	  disCodeStr="0";
	 	                      placement.setDistrictCode(Short.parseShort(dbutil.replaceNull(data.get("disCode"))));
	                         placement.setDistrictDesc(data.get("disName").toString());
	                         placement.setProvinceCode(province);
	                 }
	                 String surburb=dbutil.replaceNull(data.get("suburb"));
	 				 placement.setTown(dbutil.replaceNull(data.get("town")));
	 				/* if(!surburb.trim().equals("")){
	 					placement.setTown(surburb);
	 				 }*/
	 				 placement.setCountryCode(country);
	                 placement.setStudentContactNumber(student.getStudentContactNumber(placement.getStudentNumber()));
	                 placement.setSupervisorContactNumber(getSupervisorContactNumber(placement.getSupervisorCode(),contactDAO,contact));
	                 placement.setSchoolContactNumber(school.getSchoolContactNumber(placement.getSchoolCode()));
	                 String mentorCode=dbutil.replaceNull(data.get("mentorCode"));
				       if( mentorCode.trim().equals("")){
			        	   mentorCode="0";
			           }
			           placement.setMentorCode(Integer.parseInt(mentorCode));
			           placement.setStuFullTime(dbutil.replaceNull(data.get("stu_fulltime_sch")));
					   placement.setMentorName(dbutil.replaceNull(data.get("mentorName")));
	                   placementList.add(placement);
	           }
         return placementList;
}

  private String getSupervisorContactNumber(int supervisorCode,ContactDAO contactDAO,Contact contact) throws Exception{
                      contact  = contactDAO.getContactDetails(supervisorCode,231);
                      String contactNum="";
                      if (contact.getCellNumber()!=null && !contact.getCellNumber().trim().equalsIgnoreCase("")){
  	                        contactNum=contact.getCellNumber();
                      }else if (contact.getWorkNumber()!=null && !contact.getWorkNumber().trim().equalsIgnoreCase("")){
  	                            contactNum=contact.getWorkNumber();
                      }
                      if (contactNum==null){
  	                        contactNum="";
                      }
              return contactNum;
   }
 
  public List  getPlacementListForSuperv(int supervisorCode) throws Exception {
                                     int saCode=1015;
                                     String localPlacementsSql=getLocalPlacementListSql(supervisorCode,saCode);
                                     String internationalPlacementsSql=getInternationalPlacementListSql(supervisorCode,saCode);
                                     List supervisorPlacementList= getLocalPlacementList(localPlacementsSql);	
                                    List internationList=getInternationalPlacementList(internationalPlacementsSql);
                                     Iterator i=internationList.iterator();
                                    while(i.hasNext()){
    	                                              supervisorPlacementList.add(i.next());
                                     }
                                     return supervisorPlacementList;
   }
  private String  getLocalPlacementListSql(int supervisorCode,int saCode){
		                                 DateUtil dateutil=new DateUtil();
		               String sql="select a.mk_student_nr as stuNumber,(d.mk_title || ' ' ||  d.initials || ' ' || d.surname ) as stuName,"+
                    "  a.mk_school_code as schCode, b.name as schName, a.mk_study_unit_code as module,a.town as town,"+
                    "  a.mk_supervisor_code as supCode, (c.surname || ' ' || c.initials || ' ' || c.mk_title) as supName,"+
                   "   to_char(a.start_date,'YYYY/MM/DD') as startDate,to_char(a.end_date,'YYYY/MM/DD') as endDate,stu_fulltime_sch,"+
                   "  to_char(a.start_date_sec_prd,'YYYY/MM/DD') as startDate_sec_prd,to_char(a.end_date_sec_prd,'YYYY/MM/DD') as endDate_sec_prd,"+
                   "  a.number_of_weeks as numWeeks,a.evaluation_mark as evalMark,a.mk_academic_year  year, a.semester_period"+ 
                   " ,a.practice_period as  practiceprd,"+
                   "  semester,f.eng_description as prov,"+
                   "  a.email_to_sup as dateSent ,e.code as disCode,e.eng_description as disName,"+mentordatasql+" from tpuspl a,"+ 
                   "  tpusch b, tpusup c, stu d, ldd e, prv f"+
                   " where a.mk_academic_year="+ dateutil.yearInt()+
                   "  and a.semester_period=0"+
                   " and a.mk_school_code=b.code"+
                   " and a.mk_supervisor_code=c.code"+
                   " and a.mk_supervisor_code="+supervisorCode+
                   " and a.mk_student_nr=d.nr"+
                   " and b.mk_prv_code=f.code"+
                   " and b.mk_country_code='"+saCode+"'"+
                   " and b.mk_district_code=e.code"+
                   " order by prov,e.eng_description,c.surname";
                   
           return sql;
  }

	 private String  getInternationalPlacementListSql(int supervisorCode,int saCode){
      DateUtil dateutil=new DateUtil();
      String sql="select a.mk_student_nr as stuNumber,(d.mk_title || ' ' ||  d.initials || ' ' || d.surname ) as stuName,"+
      " a.mk_school_code as schCode, b.name as schName, a.mk_study_unit_code as module,a.town as town,"+
      " a.mk_supervisor_code as supCode, (c.surname || ' ' || c.initials || ' ' || c.mk_title) as supName,"+
     "  to_char(a.start_date,'YYYY/MM/DD') as startDate,to_char(a.end_date,'YYYY/MM/DD') as endDate,stu_fulltime_sch,"+
         "a.evaluation_mark as evalMark,a.mk_academic_year  year, a.semester_period,"+ 
     "   a.number_of_weeks as numWeeks,semester,e.eng_description,a.practice_period as  practiceprd,"+
     "  a.email_to_sup as dateSent ,"+mentordatasql+"  from tpuspl a, tpusch b, tpusup c, stu d,lns e"+
     " where a.mk_academic_year="+ dateutil.yearInt()+
     "  and a.semester_period=0"+
     " and a.mk_school_code=b.code"+
     " and a.mk_supervisor_code=c.code"+
     " and a.mk_supervisor_code="+supervisorCode+
     " and a.mk_student_nr=d.nr"+
     " and b.mk_country_code<>'"+saCode+"'"+
     " and b.mk_country_code=e.code"+
     " order by e.eng_description,c.surname";
     
     return sql;
 }
   private List getLocalPlacementList(String sql) throws Exception{
                databaseUtils dbutil=new databaseUtils();
                List placementList = new ArrayList<PlacementListRecord>();
                List queryList = dbutil.queryForList(sql,"StudentPlacementDao : Error getting placementList, reading TPUSPL / ");
                Iterator i = queryList.iterator();
                ContactDAO contactDAO=new ContactDAO();
                Contact contact=new Contact();
                School school=new School();
                Student student=new  Student(); 
                while (i.hasNext()) {
                       ListOrderedMap data = (ListOrderedMap) i.next();
                       PlacementListRecord placement = new PlacementListRecord();
                       setPlacementInfo(placement,data,dbutil);
                       setPlacementContactInfo(placement);
                       setSchoolRegionInfo(placement,data,dbutil);
                       placementList.add(placement);
               }
             return placementList;
    }
    private List getInternationalPlacementList(String sql) throws Exception{
                       databaseUtils dbutil=new databaseUtils();
                       List placementList = new ArrayList<PlacementListRecord>();
                       List queryList = dbutil.queryForList(sql,"StudentPlacementDao : Error getting placementList, reading TPUSPL / ");
                       Iterator i = queryList.iterator();
                       while (i.hasNext()) {
                            ListOrderedMap data = (ListOrderedMap) i.next();
                            PlacementListRecord placement = new PlacementListRecord();
                            setPlacementInfo(placement,data,dbutil);
                            setSchoolInternationalCountryInfo(placement,data,dbutil);
                            setPlacementContactInfo(placement);
                            placementList.add(placement);
                      }
                      return placementList;
  }   
   private void setSchoolRegionInfo(PlacementListRecord placement,ListOrderedMap data,databaseUtils dbutil){
                        placement.setDistrictCode(Short.parseShort(data.get("disCode").toString()));
                        placement.setDistrictDesc(data.get("disName").toString());
                        placement.setProvinceDescr(dbutil.replaceNull(data.get("prov")));
                        placement.setCountryDescr("SOUTH AFRICA");
    }
    private void setSchoolInternationalCountryInfo(PlacementListRecord placement,ListOrderedMap data,databaseUtils dbutil){
                      placement.setCountryDescr(dbutil.replaceNull(data.get("eng_description").toString()));
   
    }
    private void setPlacementInfo(PlacementListRecord placement,ListOrderedMap data,databaseUtils dbutil){
                                              placement.setStudentName(data.get("stuName").toString());
                  placement.setStudentNumber(Integer.parseInt(data.get("stuNumber").toString()));
                  placement.setSchoolCode(Integer.parseInt(data.get("schCode").toString()));
                  placement.setSchoolDesc(data.get("schName").toString());
                  placement.setSupervisorCode(Integer.parseInt(data.get("supCode").toString()));
                  placement.setSupervisorName(data.get("supName").toString());
                  placement.setModule(data.get("module").toString());
                  placement.setStartDate(data.get("startDate").toString());
                  placement.setEndDate(data.get("endDate").toString());
                  placement.setNumberOfWeeks(dbutil.replaceNull(data.get("numWeeks")));	
                  placement.setEvaluationMark(dbutil.replaceNull(data.get("evalMark")));	
                  placement.setEmailSendDate(dbutil.replaceNull(data.get("dateSent")));
                  placement.setStuFullTime(dbutil.replaceNull(data.get("stu_fulltime_sch")));
                  String practiceprdStr=  dbutil.replaceNull(data.get("practiceprd"));
	                 if(practiceprdStr.trim().equals(""))
	                	 practiceprdStr="0";
		                 placement.setPlacementPrd(Integer.parseInt(practiceprdStr));
	              String mentorCode=dbutil.replaceNull(data.get("mentorCode"));
			       if( mentorCode.trim().equals("")){
		        	   mentorCode="0";
		           }
		           placement.setMentorCode(Integer.parseInt(mentorCode));
		          placement.setMentorName(dbutil.replaceNull(data.get("mentorName")));
    }
    private void setPlacementContactInfo(PlacementListRecord placement )throws Exception{
                   SchoolUI schoolUI=new SchoolUI();
                   Student student=new  Student();
                   placement.setStudentContactNumber(student.getStudentContactNumber(placement.getStudentNumber()));
                   placement.setSchoolContactNumber(schoolUI.getSchoolContactNumber(placement.getSchoolCode()));
    }
    public void updateEmailToSupField(int supervisorCode) throws Exception{
                    DateUtil dateutil=new DateUtil(); 
                    String sql="update  tpuspl set  email_to_sup=sysdate where mk_academic_year="+dateutil.yearInt()+" and semester_period=0"+
         		               " and email_to_sup is null   and mk_supervisor_code <> 283   and mk_supervisor_code="+supervisorCode;
                    databaseUtils dbutil=new databaseUtils();
		            dbutil.update(sql,"StudentPlacementDAO: Error updating tpuspl ");
    }
    public boolean  isPlacementExisting(Short acadYear, Short semester, 
    		               Integer studentNr,Integer schoolCode, Integer practicePeriod,String module)throws Exception{
    	                         String sql="select count(*) from TPUSPLLOG  where action_gc201='CREATE'  and mk_student_nr="+studentNr+
    	            		                " and mk_academic_year="+acadYear+
    	                                    " and semester_period="+semester+" and mk_school_code= "+schoolCode+
    	                                    " and mk_study_unit_code='"+module+"'"+ "  and practice_period="+practicePeriod ;
    	                         String errorMsg="StudentPlacementDAO: Error quering tpuspl  to check for duplicate placement ";
    	                         databaseUtils dbutil=new databaseUtils();
    	                         int  totOcurrances=dbutil.queryInt(sql, errorMsg);
    	                         if(totOcurrances==0){
    	                        	 return false;
    	                         }else{
    	                        	 return true;
    	                         }
  }
}
