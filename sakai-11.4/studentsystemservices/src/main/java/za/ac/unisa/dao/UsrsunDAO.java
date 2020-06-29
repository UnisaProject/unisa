package za.ac.unisa.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import za.ac.unisa.model.Usrsun;

 
@Repository
@Transactional(readOnly = true)
public class UsrsunDAO {
	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}

	public List<Usrsun> getUserList() {
 		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Usrsun.class);
		@SuppressWarnings("unchecked")
		List<Usrsun> userList = criteria.list();
		return userList;
	}

	// @SuppressWarnings("unchecked")
	public List<Usrsun> getUserDetailsPerModuleYearSem(String studyUnitCode,int academicYear) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Usrsun.class);
		criteria.add(Restrictions.eq("mkStudyUnitCode", studyUnitCode));
		criteria.add(Restrictions.eq("mkAcademicYear", academicYear));
		return criteria.list();
	}

	public List<Usrsun> getModuleListPerUser(String userId,int academicYear) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Usrsun.class);
		criteria.add(Restrictions.eq("novelUserId", userId));
		criteria.add(Restrictions.eq("mkAcademicYear", academicYear));
		return criteria.list();
	}

	 
}
