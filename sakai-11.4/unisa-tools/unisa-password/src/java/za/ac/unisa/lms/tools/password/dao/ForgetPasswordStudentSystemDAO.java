package za.ac.unisa.lms.tools.password.dao;

import java.sql.Types;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;

public class ForgetPasswordStudentSystemDAO extends StudentSystemDAO  {
	
	
  
    public int getStudentLastAcadYear(String studentNr) throws Exception{
		
		int lastAcadYear = 0;
		
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		
		try{			
			lastAcadYear=jdt.queryForInt("SELECT MK_LAST_ACADEMIC_Y AS YEAR FROM  STU WHERE NR  = ?", new Object[] {studentNr});
			
		} catch (Exception ex) {
            throw new Exception("MyUnisaPassworddao: getStudentLastAcadYear: (stno: "+studentNr+") Error occurred / "+ ex,ex);
		}
		return lastAcadYear;
	}
    public String getStudentCountryCode(String studentNR) throws Exception
    {
    	JdbcTemplate jdt = new JdbcTemplate(getDataSource());
    	String counrtyCode="";
    	try{
    	String getCounrtyCode = "SELECT MK_COUNTRY_CODE FROM  STU WHERE NR = ?";    	
    	counrtyCode = (String)jdt.queryForObject(
    			getCounrtyCode, new Object[] { studentNR }, String.class);
    	if(counrtyCode.equals("")| counrtyCode==null){
    		counrtyCode="";
    	}    	
    	} catch (Exception ex) {
            throw new Exception("MyUnisaPassworddao: getStudentCountryCode: (stno: "+studentNR+") Error occurred / "+ ex,ex);
		}

      return counrtyCode;
    }
    
    /*
	 * 	update idvalt with the microsoft exchange email
	*/
	public void updateIdvaltPassword(String studentNr, String password) throws Exception{
		String sql1 = "UPDATE IDVALT "+
				  "SET password=? "+
				  "WHERE MK_STUDENT_NR = ?";

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
			jdt1.update(sql1,new Object[] {password,studentNr});
		} catch (Exception ex) {
			throw new Exception(this+ "updatePassword: Update 8 letter to IDVALT for student("+studentNr+") failed :"+ ex.getMessage());
		}
	}
    
	public void updateIdvaltEmail(String studentNr, String email) throws Exception{
		String sql1 = "UPDATE IDVALT "+
				  "SET EMAIL_ADDRESS=? "+
				  "WHERE MK_STUDENT_NR = ?";

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
			jdt1.update(sql1,new Object[] {email,studentNr});
		} catch (Exception ex) {
			throw new Exception(this+ "updateIdvaltEmail: Update mmyLife to IDVALT for student("+studentNr+") failed :"+ ex.getMessage());
		}
	}
	
	public void updateAdrphEmail(String studentNr, String email) throws Exception{
		String sql1 = "UPDATE ADRPH "+
				  "SET EMAIL_ADDRESS=? "+
				  "WHERE REFERENCE_NO = ?";

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
			jdt1.update(sql1,new Object[] {email,studentNr});
		} catch (Exception ex) {
			throw new Exception(this+ "updateAdrphEmail: Update mmyLife to ADRPH for student("+studentNr+") failed :"+ ex.getMessage());
		}
	}

}
