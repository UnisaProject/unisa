package za.ac.unisa.lms.tools.brochures.constants;
	/**
	 * 
	 * @param colCode
	 * @param schCode
	 * @param dptCode
	 * @param year
	 * @return
	 */
public class SLPQuery {

	public String mySLPQuery(String collegeCode, String schoolCode, String deptCode, int year){
	
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("select ");
		stringBuilder.append(" (select colleg.ENG_DESCRIPTION from colleg where colleg.code =  grd.COLLEGE_CODE) collegeDesc,");
		stringBuilder.append(" grd.COLLEGE_CODE collegeCode,");
		stringBuilder.append(" (select school.ENG_DESCRIPTION from school");
		stringBuilder.append(" where school.code = grd.SCHOOL_CODE and school.COLLEGE_CODE=grd.COLLEGE_CODE) schoolDesc,");
		stringBuilder.append(" (select dpt.ENG_DESCRIPTION from dpt where dpt.code = grd.MK_DEPARTMENT_CODE) departmentDesc,");
		stringBuilder.append(" grd.MK_DEPARTMENT_CODE departmentCode,");
		stringBuilder.append(" (select kat.ENG_DESCRIPTION from kat where kat.code = grd.FK_KATCODE) qualCategory,");
		stringBuilder.append(" grd.LONG_ENG_DESCRIPTI qualDescription,");
		stringBuilder.append(" quaslp.MK_QUALIFICATION_CODE code, quaslp.MK_ACADEMIC_YEAR year, NVL(quaslp.TARGET_GROUP,' ') TARGET_GROUP,"); 
		stringBuilder.append(" NVL(quaslp.ADMISSION_REQUIREMENTS,' ') adminReq, NVL(quaslp.DURATION,' ') duration,");
		stringBuilder.append(" NVL(quaslp.LANGUAGE_MEDIUM,' ') languageMedium, NVL(quaslp.REGISTRATION_PERIODS,' ') registrationPeriods,");
		stringBuilder.append(" NVL(quaslp.TUITION_METHOD,' ') tuitionMethod, NVL(quaslp.KIND_OF_ASSESSMENT,' ') kindOfAssessment,");  
		stringBuilder.append(" sununs.MK_STUDY_UNIT_CODE studyUnitCode, sun.ENG_LONG_DESCRIPTI studyUnitDesc,");
		stringBuilder.append(" NVL(sununs.purpose,' ') studyUnitContent,");
		stringBuilder.append(" NVL(quaslp.COURSE_LEADER_DETAILS,' ') leaderDetails,");
		stringBuilder.append(" NVL(quaslp.PROGRAMME_ADMINISTRATOR_DETAIL,' ') programmeAdmin,");			
		stringBuilder.append(" NVL(GRD.PURPOSE,' ') purposeStatement");
		stringBuilder.append(" from quaslp, grd, qspsun, SUN, SUNUNS");
		stringBuilder.append(" where quaslp.mk_qualification_code  = grd.code");
		stringBuilder.append(" AND quaslp.MK_ACADEMIC_YEAR = "+year);
		stringBuilder.append(" AND qspsun.MK_QUAL_CODE = grd.CODE"); 
		stringBuilder.append(" AND sununs.MK_STUDY_UNIT_CODE = qspsun.MK_STUDY_UNIT_CODE");
		stringBuilder.append(" AND sun.CODE = sununs.MK_STUDY_UNIT_CODE");

		if(! collegeCode.equals("-1")){
			stringBuilder.append(" AND grd.COLLEGE_CODE ="+"'"+collegeCode+"'"); 			
		}
		if(! schoolCode.equals("-1")){
			stringBuilder.append(" AND grd.SCHOOL_CODE ="+"'"+schoolCode+"'"); 
		}
		if(! deptCode.equals("-1")){
			stringBuilder.append(" AND grd.MK_DEPARTMENT_CODE ="+"'"+deptCode+"'"); 			
		}
		
		stringBuilder.append(" order by collegeDesc, departmentDesc, qualCategory, grd.CODE");
		
		return stringBuilder.toString();
	}

}