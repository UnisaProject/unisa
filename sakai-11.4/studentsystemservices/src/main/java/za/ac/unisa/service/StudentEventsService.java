package za.ac.unisa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import za.ac.unisa.dao.StudentEventsServiceDAO;
import za.ac.unisa.model.StudentEventsBean;
 

@Service
@Transactional
public class StudentEventsService {

	private StudentEventsServiceDAO studentEventsServiceDAO;

	@Autowired
	public void setUserDAO(StudentEventsServiceDAO studentEventsServiceDAO) {
		this.studentEventsServiceDAO = studentEventsServiceDAO;
	}

	public List<StudentEventsBean> getStudentEvents(String module,int studentnr) {
		return this.studentEventsServiceDAO.studentEvents(module, studentnr);
	}
}
