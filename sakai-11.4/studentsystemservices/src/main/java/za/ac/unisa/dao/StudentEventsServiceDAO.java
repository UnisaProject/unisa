package za.ac.unisa.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import za.ac.unisa.controller.StudentSystemServicesController;
import za.ac.unisa.model.StudentEventsBean;

@Repository
@Transactional(readOnly = true)
public class StudentEventsServiceDAO {

	private SessionFactory sessionFactory;
	private static Log logger = LogFactory.getLog(StudentEventsServiceDAO.class);

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}

	public List studentEvents(String module,int studentnr) {

		List<StudentEventsBean> list = new ArrayList<StudentEventsBean>();
		
		try {
			String queryString = "select student_nr \"studentNumber\",surname||' '||initials||' '||mk_title \"studentName\",\r\n" + 
					"to_char(timestamp,'YYYYYMMDD HH24:MI:SS') \"timestamp\",category \"categoryName\",\r\n" + 
					"nvl((select eng_description from gencod where fk_gencatcode = 359 and gencod.code = category),' ') \"categoryDescription\",description \"Detail\", sequence_nr \"sequenceNumber\"\r\n" + 
					" from solgen, stu where system_gc6 = 'EXAMS' and student_nr ="+studentnr + 
					" and stu.nr = student_nr and (course = '"+module+"' or category = 'LOGIN')\r\n" + 
					"order by sequence_nr";

			SQLQuery sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(queryString);
			List<Object[]> results = sqlQuery.list();
					  
			Iterator it = results.iterator();
			while (it.hasNext()) {
				Object[] line = (Object[]) it.next();
				StudentEventsBean studentEventsBean = new StudentEventsBean();
				studentEventsBean.setStudentNr(line[0].toString());
				studentEventsBean.setStudentName(line[1].toString());
				studentEventsBean.setTimestamp(line[2].toString());
				studentEventsBean.setCategoryName(line[3].toString());
				studentEventsBean.setCategoryDescription(line[4].toString());
				studentEventsBean.setDetail(line[5].toString());
				studentEventsBean.setSequenceNumber(line[6].toString());
				
				list.add(studentEventsBean);
			}
		} catch (HibernateException e) {
			logger.error(this+" error on studentEvents "+e.getMessage());
			e.printStackTrace();
		}
		
		return list;
	}

}
