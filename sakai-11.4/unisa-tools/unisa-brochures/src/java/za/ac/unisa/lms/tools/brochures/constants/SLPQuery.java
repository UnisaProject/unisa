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

		stringBuilder.append("select quaslp.MK_QUALIFICATION_CODE code, quaslp.MK_ACADEMIC_YEAR year, quaslp.TARGET_GROUP,"); stringBuilder.append(" quaslp.ADMISSION_REQUIREMENTS adminReq, quaslp.DURATION duration,");
		stringBuilder.append(" quaslp.LANGUAGE_MEDIUM languageMedium, quaslp.REGISTRATION_PERIODS registrationPeriods,");
		stringBuilder.append(" quaslp.TUITION_METHOD tuitionMethod, quaslp.KIND_OF_ASSESSMENT kindOfAssessment,");
		stringBuilder.append(" quaslp.COURSE_LEADER_DETAILS leaderDetails,");
		stringBuilder.append(" quaslp.PROGRAMME_ADMINISTRATOR_DETAIL programmeAdmin,");			
		stringBuilder.append(" GRD.PURPOS purposeStatement");
		stringBuilder.append(" from quaslp,grd");
		stringBuilder.append(" where quaslp.mk_qualification_code  = grd.code");
		stringBuilder.append(" AND year = "+year);

		if(! collegeCode.equals("-1")){
			stringBuilder.append(" AND grd.COLLEGE_CODE ="+"'"+collegeCode+"'"); 			
		}
		if(! schoolCode.equals("-1")){
			stringBuilder.append(" AND grd.SCHOOL_CODE ="+"'"+schoolCode+"'"); 
		}
		if(! deptCode.equals("-1")){
			stringBuilder.append(" AND grd.MK_DEPARTMENT_CODE ="+"'"+deptCode+"'"); 			
		}
		
		return stringBuilder.toString();
	}

}