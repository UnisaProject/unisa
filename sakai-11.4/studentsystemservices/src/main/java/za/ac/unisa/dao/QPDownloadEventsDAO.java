package za.ac.unisa.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import za.ac.unisa.model.QPDownloadEventsBean;
import za.ac.unisa.model.Solgen;

@Repository
@Transactional(readOnly = true)
public class QPDownloadEventsDAO {

	private SessionFactory sessionFactory;
	private static Log logger = LogFactory.getLog(QPDownloadEventsDAO.class);

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}

	public List getQuestionPaperDownloadEvents() {

		List<QPDownloadEventsBean> list = new ArrayList<QPDownloadEventsBean>();

		String queryString = " select t1.course as \"module\", to_char(t1.timestamp,'YYYY/MM/DD HH24:MI') as \"timestamp\", to_char(t1.timestamp,'YYYY/MM/DD') as \"date\", to_char(t1.timestamp,'HH24') as \"hour\", to_char(t1.timestamp,'MI') as \"minute\",\r\n"
				+ "        (select count( distinct student_nr) from solgen t2\r\n"
				+ " where to_char(t2.timestamp,'YYYY/MM/DD HH24:MI') = to_char(t1.timestamp,'YYYY/MM/DD HH24:MI') and t2.course = t1.course\r\n"
				+ "and t2.category in ('DOWNLOAD') and T2.SYSTEM_GC6 = 'EXAMS') \"downloaded\",\r\n"
				+ "(select count(distinct student_nr) from solgen t2\r\n"
				+ "where to_char(t2.timestamp,'YYYY/MM/DD HH24:MI') = to_char(t1.timestamp,'YYYY/MM/DD HH24:MI') and t2.course = t1.course\r\n"
				+ " and t2.category in ('MC4','WR3') and T2.SYSTEM_GC6 = 'EXAMS') \"submitted\",\r\n"
				+ " count (distinct student_nr) \"students\" from solgen t1 where t1.system_gc6 = 'EXAMS'  "
				+ "         and t1.category in ('MC4','WR3','DOWNLOAD')\r\n"
				+ " and to_char(t1.timestamp,'YYYY/MM/DD') = to_char(sysdate,'YYYY/MM/DD') and to_char(t1.timestamp,'HH24MI') >= ( to_char(sysdate,'HH24MI') - 200 )\r\n"
				+ " group by t1.course, to_char(t1.timestamp,'YYYY/MM/DD HH24:MI'),to_char(t1.timestamp,'YYYY/MM/DD'), to_char(t1.timestamp,'HH24'), to_char(t1.timestamp,'MI') \r\n"
				+ " order by t1.course, to_char(t1.timestamp,'YYYY/MM/DD HH24:MI'),to_char(t1.timestamp,'YYYY/MM/DD'), to_char(t1.timestamp,'HH24'), to_char(t1.timestamp,'MI') desc\r\n";

		try {
			SQLQuery sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(queryString);
			List<Object[]> results = sqlQuery.list();

			Iterator it = results.iterator();
			while (it.hasNext()) {
				Object[] line = (Object[]) it.next();
				QPDownloadEventsBean qpDownloadEventsBean = new QPDownloadEventsBean();
				qpDownloadEventsBean.setModule(line[0].toString());
				qpDownloadEventsBean.setTimestamp(line[1].toString());
				qpDownloadEventsBean.setDate(line[2].toString());
				qpDownloadEventsBean.setHour(line[3].toString());
				qpDownloadEventsBean.setMinute(line[4].toString());
				qpDownloadEventsBean.setDownloaded(line[5].toString());
				qpDownloadEventsBean.setSubmitted(line[6].toString());
				qpDownloadEventsBean.setStudents(line[7].toString());

				list.add(qpDownloadEventsBean);
			}
		} catch (HibernateException e) {
			logger.error(this + " error on getQuestionPaperDownloadEvents " + e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	public List getQuestionPaperDownloadEventsPerModule(String module) {

		List<QPDownloadEventsBean> list = new ArrayList<QPDownloadEventsBean>();

		String queryString = " select t1.course as \"module\", to_char(t1.timestamp,'YYYY/MM/DD HH24:MI') as \"timestamp\", to_char(t1.timestamp,'YYYY/MM/DD') as \"date\", to_char(t1.timestamp,'HH24') as \"hour\", to_char(t1.timestamp,'MI') as \"minute\",\r\n"
				+ "        (select count( distinct student_nr) from solgen t2\r\n"
				+ " where to_char(t2.timestamp,'YYYY/MM/DD HH24:MI') = to_char(t1.timestamp,'YYYY/MM/DD HH24:MI') and t2.course = t1.course\r\n"
				+ "and t2.category in ('DOWNLOAD') and T2.SYSTEM_GC6 = 'EXAMS') \"downloaded\",\r\n"
				+ "(select count(distinct student_nr) from solgen t2\r\n"
				+ "where to_char(t2.timestamp,'YYYY/MM/DD HH24:MI') = to_char(t1.timestamp,'YYYY/MM/DD HH24:MI') and t2.course = t1.course\r\n"
				+ " and t2.category in ('MC4','WR3') and T2.SYSTEM_GC6 = 'EXAMS') \"submitted\",\r\n"
				+ " count (distinct student_nr) \"students\" from solgen t1 where t1.system_gc6 = 'EXAMS' and t1.course = '"
				+ module + "'" + "   and t1.category in ('MC4','WR3','DOWNLOAD')\r\n"
				+ " and to_char(t1.timestamp,'YYYY/MM/DD') = to_char(sysdate,'YYYY/MM/DD') and to_char(t1.timestamp,'HH24MI') >= ( to_char(sysdate,'HH24MI') - 200 )\r\n"
				+ " group by t1.course, to_char(t1.timestamp,'YYYY/MM/DD HH24:MI'),to_char(t1.timestamp,'YYYY/MM/DD'), to_char(t1.timestamp,'HH24'), to_char(t1.timestamp,'MI') \r\n"
				+ " order by t1.course, to_char(t1.timestamp,'YYYY/MM/DD HH24:MI'),to_char(t1.timestamp,'YYYY/MM/DD'), to_char(t1.timestamp,'HH24'), to_char(t1.timestamp,'MI') desc\r\n";

		try {
			SQLQuery sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(queryString);
			List<Object[]> results = sqlQuery.list();

			Iterator it = results.iterator();
			while (it.hasNext()) {
				Object[] line = (Object[]) it.next();
				QPDownloadEventsBean qpDownloadEventsBean = new QPDownloadEventsBean();
				qpDownloadEventsBean.setModule(line[0].toString());
				qpDownloadEventsBean.setTimestamp(line[1].toString());
				qpDownloadEventsBean.setDate(line[2].toString());
				qpDownloadEventsBean.setHour(line[3].toString());
				qpDownloadEventsBean.setMinute(line[4].toString());
				qpDownloadEventsBean.setDownloaded(line[5].toString());
				qpDownloadEventsBean.setSubmitted(line[6].toString());
				qpDownloadEventsBean.setStudents(line[7].toString());

				list.add(qpDownloadEventsBean);
			}
		} catch (HibernateException e) {
			logger.error(this + " error on getQuestionPaperDownloadEventsPerModule for module "+module+" " + e.getMessage());
			e.printStackTrace();
		}
		return list;
	}
}
